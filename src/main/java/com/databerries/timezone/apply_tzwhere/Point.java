package com.databerries.timezone.apply_tzwhere;

/**
 * Created by smasue on 6/7/17.
 * Class representing a geo point
 */
public class Point {

  private double latitude;
  private double longitude;
  private int index;
  private String timezone;

  Point(double latitude, double longitude, int index, String timezone) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.index = index;
    this.timezone = timezone;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public int getIndex() {
    return index;
  }

  public String getTimezone() {
    return timezone;
  }

  @Override
  public String toString() {
    return latitude + "," + longitude + "," + timezone + "," + index;
  }
}
