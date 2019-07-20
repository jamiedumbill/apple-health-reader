package io.github.jamiedumbill.applehealthreader;

import io.github.jamiedumbill.applehealthreader.handler.AppleHealthRecordHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Parses an Apple Health .xml file to a .csv or returns a Collection of POJO's
 */
public class AppleHealthReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppleHealthReader.class);
    private static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * Will either log stats detailing number of records found by type or will write records to a csv file.
     *
     * @param args expects either just an input file (.xml) or 2x args an input file (.xml) and an output file (.csv)
     */
    public static void main(String[] args) {
        LOGGER.info("Starting AppleHealthReader...");
        Collection<AppleHealthRecord> records = read(args[0]);
        if (args.length == 2) {
            writeToFile(args[1], records);
        } else {
            LOGGER.info("No output file to write data to log only");
        }
        LOGGER.info("Finished AppleHealthReader...");
    }

    /**
     * Takes an Apple Health xml file and returns a collection of POJO {@link AppleHealthRecord}
     *
     * @param xmlFilePath an xml file containing Apple Health records
     * @return a collection of AppleHealthRecord
     */
    static Collection<AppleHealthRecord> read(String xmlFilePath) {
        LOGGER.info("Reading {}", xmlFilePath);
        SAXParserFactory spfac = getSaxParserFactory();
        SAXParser sp = getSaxParser(spfac);
        AppleHealthRecordHandler handler = getAppleHealthRecordHandler(xmlFilePath, sp);
        handler.groupByCountRecordTypes().forEach((k, v) -> LOGGER.info("{}: {}", k, v));
        LOGGER.info("Found record types of: {}", handler.recordTypes());
        Collection<AppleHealthRecord> records = handler.readRecords();
        LOGGER.info("Found {} records", records.size());
        return records;

    }

    private static SAXParserFactory getSaxParserFactory() {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        try {
            spfac.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException e) {
            LOGGER.error("Error setting feature on SAXParserFactory", e);
        }
        return spfac;
    }

    private static AppleHealthRecordHandler getAppleHealthRecordHandler(String xmlFilePath, SAXParser sp) {
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
        return handler;
    }

    private static SAXParser getSaxParser(SAXParserFactory spfac) {
        SAXParser sp = null;
        try {
            sp = spfac.newSAXParser();
        } catch (ParserConfigurationException e) {
            LOGGER.error("Parser configuration error", e);
        } catch (SAXException e) {
            LOGGER.error("SAX error", e);
        }
        return sp;
    }

    /**
     * Takes records and writes them to a .csv file
     * @param filePath path to write records to
     * @param records data to write to the file
     */
    static void writeToFile(String filePath, Collection<AppleHealthRecord> records) {
        LOGGER.info("Writing to {}", filePath);
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
