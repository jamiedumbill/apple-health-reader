package io.github.jamiedumbill.applehealthreader;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppleHealthRecord {

    private final ZonedDateTime timeCreated;
    private final Double value;

    /**
     * Simple timeseries record from apple health data
     * @param startDate the time the records was created
     * @param value sample value of the measurement/records
     */
    public AppleHealthRecord(String startDate, String value) {
        this.timeCreated = ZonedDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
        this.value = Double.valueOf(value);
    }

    /**
     * .csv is a useful universal format
     * @return single line of a csv file
     */
    String toCsv(){
        return timeCreated + "," +value;
    }

    @Override
    public String toString() {
        return "AppleHealthRecord{" +
                "timeCreated=" + timeCreated +
                ", value=" + value +
                '}';
    }

}
