package io.github.jamiedumbill.applehealthreader;

import io.github.jamiedumbill.applehealthreader.handler.AppleHealthRecordHandler;
import io.github.jamiedumbill.applehealthreader.handler.BodyFatPercentAppleHealthRecordHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public class AppleHealthReader {


    private static final Logger LOGGER = LoggerFactory.getLogger(AppleHealthReader.class);

    public static void main(String[] args){
        LOGGER.info("Starting AppleHealthReader...");
        read(args[0]);
        LOGGER.info("Finished AppleHealthReader...");
    }

    static Collection<AppleHealthRecord> read(String xmlFilePath) {
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

        AppleHealthRecordHandler handler = new BodyFatPercentAppleHealthRecordHandler();

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
