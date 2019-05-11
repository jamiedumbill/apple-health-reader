# Apple Health Reader
Mini project to read Apple Health export xml.

## Export Apple Health data
Health > Health Data > Profile > Export Health Data

## Build
```
mvn clean package
```

## Usage

```
Where
hr is heart rate
bm is body mass
bf is body fat

java -jar apple-health-reader-1.0-SNAPSHOT-full.jar ./export.xml [hr|bm|bf]
```