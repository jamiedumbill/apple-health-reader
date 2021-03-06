package io.github.jamiedumbill.applehealthreader;

import org.xml.sax.Attributes;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Immutable Apple Health records
 */
public class AppleHealthRecord {

    private final String type;
    private final String unit;
    private final ZonedDateTime timeCreated;
    private final Double value;

    /**
     * Simple timeseries record from apple health data
     *
     * @param type      data type of the record
     * @param unit      of measurement
     * @param startDate the time the records was created
     * @param value     sample value of the measurement/records
     */
    AppleHealthRecord(String type, String unit, String startDate, String value) {
        this.type = type;
        this.unit = unit;
        this.timeCreated = ZonedDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
        this.value = Double.valueOf(value);
    }

    public static AppleHealthRecord createFromXMLAttributes(Attributes attributes){
        return new AppleHealthRecord(attributes.getValue("type"),
                attributes.getValue("unit"),
                attributes.getValue("startDate"),
                attributes.getValue("value"));
    }

    public String getType() {
        return type;
    }

    /**
     * .csv is a useful universal format
     *
     * @return single line of a csv file
     */
    String toCsv() {
        return type + "," + unit + "," + timeCreated + "," + value;
    }

    @Override
    public String toString() {
        return "AppleHealthRecord{" +
                "type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                ", timeCreated=" + timeCreated +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppleHealthRecord)) return false;
        AppleHealthRecord appleHealthRecord = (AppleHealthRecord) o;
        return Objects.equals(type, appleHealthRecord.type) &&
                Objects.equals(unit, appleHealthRecord.unit) &&
                Objects.equals(timeCreated, appleHealthRecord.timeCreated) &&
                Objects.equals(value, appleHealthRecord.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, unit, timeCreated, value);
    }
}
