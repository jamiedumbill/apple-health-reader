package io.github.jamiedumbill.applehealthreader;

import io.github.jamiedumbill.applehealthreader.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AppleHealthReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppleHealthReader.class);
    private final Map<String, AppleHealthRecordHandler> handlerMap = new HashMap<>();

    public static void main(String[] args){
        LOGGER.info("Starting AppleHealthReader...");
        AppleHealthRecordHandler handler = pickHandler(args[1]);
        read(args[0], handler);
        LOGGER.info("Finished AppleHealthReader...");
    }

    static AppleHealthRecordHandler pickHandler(String option){
        LOGGER.info("Checking which handler to use");
        if(option.equals("bf")){
            LOGGER.info("Using {}", BodyFatPercentAppleHealthRecordHandler.class);
            return new BodyFatPercentAppleHealthRecordHandler();
        }
        if(option.equals("bm")){
            LOGGER.info("Using {}", BodyMassAppleHealthRecordHandler.class);
            return new BodyMassAppleHealthRecordHandler();
        }
        if(option.equals("hr")){
            LOGGER.info("Using {}", HeartRateAppleHealthRecordHandler.class);
            return new HeartRateAppleHealthRecordHandler();
        }
        LOGGER.info("Using default {}", DefaultAppleHealthRecordHandler.class);
        return new DefaultAppleHealthRecordHandler();
    }

    static Collection<AppleHealthRecord> read(String xmlFilePath, AppleHealthRecordHandler handler) {
        LOGGER.info("Reading {}", xmlFilePath);

        SAXParserFactory spfac = SAXParserFactory.newInstance();

        SAXParser sp = null;
        try {
            sp = spfac.newSAXParser();
        } catch (ParserConfigurationException e) {
            LOGGER.error("Parser configuration error", e);
        } catch (SAXException e) {
            LOGGER.error("SAX error", e);
        }

        try {
            if (sp != null) {
                sp.parse(xmlFilePath, handler);
            } else {
                LOGGER.error("SAXParser not set");
            }
        } catch (SAXException e) {
            LOGGER.error("SAX error", e);
        } catch (FileNotFoundException e ){
            LOGGER.error("Could not find the file {} please check it exists", xmlFilePath);
        } catch (IOException e) {
            LOGGER.error("IO error", e);
        }
        Collection<AppleHealthRecord> records = handler.readRecords();
        LOGGER.info("Found {} records", records.size());
        return records;

    }
}
