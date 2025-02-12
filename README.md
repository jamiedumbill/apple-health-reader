[![Java CI with Maven](https://github.com/jamiedumbill/apple-health-reader/actions/workflows/maven.yml/badge.svg)](https://github.com/jamiedumbill/apple-health-reader/actions/workflows/maven.yml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jamiedumbill_apple-health-reader&metric=alert_status)](https://sonarcloud.io/dashboard?id=jamiedumbill_apple-health-reader)
----
# Apple Health Reader
Mini project to read Apple Health export xml.

## Export Apple Health data
Health > Health Data > Profile > Export Health Data

## Build
```
mvn clean package
```

## Usage

### CLI

Reads export.xml and writes a .csv file of simplified health records
```
java -jar apple-health-reader-1.0-SNAPSHOT-full.jar ./export.xml ./output.csv
```

To log only exclude the output file
```
java -jar apple-health-reader-1.0-SNAPSHOT-full.jar ./export.xml
```

### Library
Read your Apple Health xml in to a Collection
```
Collection<AppleHealthRecord> records = AppleHealthReader.read("export.xml");
```
