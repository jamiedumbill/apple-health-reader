package io.github.jamiedumbill.applehealthreader.handler;

import io.github.jamiedumbill.applehealthreader.AppleHealthRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AppleHealthRecordHandler extends DefaultHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final String recordType;
    private int counter=0;
    private Collection<AppleHealthRecord> records = new ArrayList<>();

    AppleHealthRecordHandler(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("Record") && attributes.getValue("type").equals(recordType)) {
            AppleHealthRecord record = new AppleHealthRecord(attributes.getValue("startDate"), attributes.getValue("value"));
            records.add(record);
            counter++;
            if(counter%10 == 0){
                LOGGER.info("Found {} records", counter);
            }
        }
    }

    public Collection<AppleHealthRecord> readRecords() {
        return records;
    }
}
