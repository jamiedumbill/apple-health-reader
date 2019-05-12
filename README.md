[![Build Status](https://travis-ci.org/jamiedumbill/apple-health-reader.svg?branch=master)](https://travis-ci.org/jamiedumbill/apple-health-reader)
# Apple Health Reader
Mini project to read Apple Health export xml.

## Export Apple Health data
Health > Health Data > Profile > Export Health Data

## Build
```
mvn clean package
```

## Usage

Reads export.xml and writes a .csv file of simplified health records
```
java -jar apple-health-reader-1.0-SNAPSHOT-full.jar ./export.xml ./output.csv
```

To log only exclude the output file
```
java -jar apple-health-reader-1.0-SNAPSHOT-full.jar ./export.xml
```