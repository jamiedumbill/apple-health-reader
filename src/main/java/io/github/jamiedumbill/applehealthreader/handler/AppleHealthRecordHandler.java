package io.github.jamiedumbill.applehealthreader.handler;

import io.github.jamiedumbill.applehealthreader.AppleHealthRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AppleHealthRecordHandler extends DefaultHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final String recordType;
    private int counter = 0;
    private Collection<AppleHealthRecord> records = new ArrayList<>();

    AppleHealthRecordHandler(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (isNumericRecord(qName, attributes)) {
            AppleHealthRecord record = new AppleHealthRecord(attributes.getValue("type"), attributes.getValue("unit"), attributes.getValue("startDate"), attributes.getValue("value"));
            records.add(record);
            counter++;
            if (counter % 100000 == 0) {
                LOGGER.info("Found {} records", counter);
            }
        }
    }

    private boolean isNumericRecord(String qName, Attributes attributes) {
        if (!qName.equalsIgnoreCase("Record")) {
            return false;
        }
        if (!attributes.getValue("type").matches(recordType)) {
            return false;
        }
        if (attributes.getValue("value") == null) {
            return false;
        }
        return attributes.getValue("value").matches("^[+-]?(\\d*\\.)?\\d+$");
    }

    public Collection<AppleHealthRecord> readRecords() {
        return records;
    }

    public Collection<String> recordTypes(){
        return readRecords().stream().map(AppleHealthRecord::getType).distinct().collect(Collectors.toList());
    }

    public Map<String, Long> groupByCountRecordTypes(){
        return readRecords().stream().map(AppleHealthRecord::getType).collect(Collectors.groupingBy(r -> r, Collectors.counting()));
    }
}
