package io.github.jamiedumbill.applehealthreader;

import io.github.jamiedumbill.applehealthreader.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AppleHealthReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppleHealthReader.class);
    private static final String NEW_LINE = System.getProperty("line.separator");

    public static void main(String[] args) {
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
        AppleHealthRecordHandler handler = new AppleHealthRecordHandler();
        try {
            if (sp != null) {
                sp.parse(xmlFilePath, handler);
            } else {
                LOGGER.error("SAXParser not set");
            }
        } catch (SAXException e) {
            LOGGER.error("SAX error", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find the file {} please check it exists", xmlFilePath);
        } catch (IOException e) {
            LOGGER.error("IO error", e);
        }

        handler.groupByCountRecordTypes().forEach((k, v) -> LOGGER.info("{}: {}", k, v));
        LOGGER.info("Found record types of: {}", handler.recordTypes());
        Collection<AppleHealthRecord> records = handler.readRecords();
        LOGGER.info("Found {} records", records.size());
        return records;

    }

    private void writeToFile(String filePath, Collection<AppleHealthRecord> records) {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            for (AppleHealthRecord record : records) {
                outputStream.write(record.toCsv().getBytes());
                outputStream.write(NEW_LINE.getBytes());
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find the file {} please check it exists", filePath);
        } catch (IOException e) {
            LOGGER.error("Problem writing records to {}", filePath);
        }
    }
}
