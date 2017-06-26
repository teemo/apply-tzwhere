package com.databerries.localdate;

import com.savi.geo.timezones.TzWhere;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

/**
 * Created by smasue on 6/8/17.
 *
 * Short algo used to populated (using TzWhere) a dataset of lat/long with the timezone associated.
 *
 * - Takes a csv of:
 * latitude,longitude,index
 * - Returns a csv of:
 * latitude,longitude,timezone,index
 */
class Executor {

  private void processTimeZones(String pathInputFile, float step) {
    System.out.println("START " + new Date().toString());
    float halfStep = step / 2;

    // transform a csv line in a Point object
    Function<String, Point> mapLineToPoint = (line) -> {
      String[] p = line.split(",");

      // add half a step to place back the location in the middle of the square
      double latitude = Double.parseDouble(p[0]);
      double longitude = Double.parseDouble(p[1]);
      int index = Integer.parseInt(p[2]);

      // Use the external lib TzWhere to get the timezone
      String timezone = TzWhere.getLatLongTimeZoneId(latitude + halfStep, longitude + halfStep);

      // nullify not found timezone and uninhabited
      if (timezone == null || "uninhabited".equalsIgnoreCase(timezone) || ""
          .equalsIgnoreCase(timezone)) {
        timezone = "null";
      }
        // fix typo from TZWhere
      else if ("America/Monterey".equalsIgnoreCase(timezone)) // typo in the library
      {
        timezone = "America/Monterrey";
      }

      return new Point(latitude, longitude, index, timezone);
    };

    File file = new File(pathInputFile);
    String outputFileName = generateOutputFileName(step);

    try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFileName)));
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
      // read all lines
      List<String> row_lines = br.lines().collect(Collectors.toList());

      // get all points from the lines
      List<Point> points = row_lines.parallelStream().map(mapLineToPoint)
          .collect(Collectors.toList());

      // using the index, sort all the point and write then in the csv
      points.stream().sorted(comparingInt(Point::getIndex)).forEach(pw::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("END " + new Date().toString());
  }

  private static String generateOutputFileName(float step) {
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String step_text = ("" + step).replace(".", "");
    String date_text = date.format(formatter);
    return "lat_long_timezone_step" + step_text + "_" + date_text + ".csv";
  }

  public static void main(String[] args) {
    String path = args[0];
    String stepArg = args[1];
    float step = Float.parseFloat(stepArg);

    System.out.println("Args: " + path + ", " + step);
    new Executor().processTimeZones(path, step);
  }
}
