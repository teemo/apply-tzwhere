# apply-tzwhere
The library takes a csv file generated with [lat-long-points-generator](https://github.com/databerries/lat-long-points-generator) and find the timezones associated for each latitude/longitude. The result is stored in a csv file. 

csv line:
latitude, longitude, index

## the index
An index must be provided in the original data to assure the order is kept in the result. 

## the step
The lib needs a __step__ as argument. Indeed, the points using the __lat-long-points-generator__ were placed in the left-bottom corner. So, in order to determine the timezones in the center of the squares we have to add a half step to each lat/long:

```
             ___________________ (30, 120)        _________________________ 
             |. |. |. |. |. |. |                  | · | · | · | · | · | · |
             ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻                  ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻
             |. |. |. |. |. |. |             ->   | · | · | · | · | · | · |
             ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻                  ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻
             |. |. |. |. |. |. |                  | · | · | · | · | · | · |
(-90, -180)  ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻                  ⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻
```
> please, refer to the documentation of [lat-long-points-generator](https://github.com/databerries/lat-long-points-generator).
for more information.

## example of input and output
Eg. 
Input:
```
51.15,2.2,1
51.15,2.25,2
51.15,2.3,3
51.15,2.35,4
51.15,2.4,5
51.15,2.45,6
51.15,2.5,7
51.2,2.35,8
51.2,2.4,9
51.2,2.45,10
```
Ouput:
```
51.15,2.2,Europe/Paris,1
51.15,2.25,Europe/Paris,2
51.15,2.3,Europe/Paris,3
51.15,2.35,Europe/Paris,4
51.15,2.4,Europe/Paris,5
51.15,2.45,Europe/Paris,6
51.15,2.5,Europe/Paris,7
51.2,2.35,Europe/Paris,8
51.2,2.4,Europe/Paris,9
51.2,2.45,Europe/Paris,10
```
## How to use 
```bash
$ mvn package
$ cd target
$ java -cp timezone-apply-tzwhere-0.0.1-SNAPSHOT.jar com.databerries.timezone.apply_tzwhere.Executor ~/lat_long_index_step005_20170608182001.csv 0.05
```
