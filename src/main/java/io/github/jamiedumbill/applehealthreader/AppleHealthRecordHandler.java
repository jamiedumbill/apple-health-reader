package io.github.jamiedumbill.applehealthreader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class AppleHealthRecordHandler extends DefaultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppleHealthRecordHandler.class);
    private int counter = 0;
    private Collection<AppleHealthRecord> records = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (isNumericRecord(qName, attributes)) {
            AppleHealthRecord appleHealthRecord = AppleHealthRecord.createFromXMLAttributes(attributes);
            records.add(appleHealthRecord);
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
        String recordValue = attributes.getValue("value");
        if (recordValue == null) {
            return false;
        }
        return isNumeric(recordValue);
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    Collection<AppleHealthRecord> readRecords() {
        return records;
    }

    Collection<String> recordTypes() {
        return readRecords().stream().map(AppleHealthRecord::getType).distinct().toList();
    }

    Map<String, Long> groupByCountRecordTypes() {
        return readRecords().stream().map(AppleHealthRecord::getType).collect(Collectors.groupingBy(r -> r, Collectors.counting()));
    }
}
