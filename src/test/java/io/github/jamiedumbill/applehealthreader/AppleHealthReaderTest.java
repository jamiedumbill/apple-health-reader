package io.github.jamiedumbill.applehealthreader;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppleHealthReaderTest {

    private static final String CONSOLIDATED_FILE = getTestArtifact("./apple-export.xml");

    static String getTestArtifact(String filename) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename)).getPath();
    }

    @Test
    void readBodyFat() {
        final String bodyFatFile = getTestArtifact("./apple-body-fat-percent.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyFatFile);
        assertEquals(2, records.size());
    }

    @Test
    void readBodyMass() {
        final String bodyMassFile = getTestArtifact("./apple-body-mass.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyMassFile);
        assertEquals(3, records.size());
    }

    @Test
    void readHeartRate() {
        final String heartRateFile = getTestArtifact("./apple-heart-rate.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(heartRateFile);
        assertEquals(5, records.size());
    }

    @Test
    void readCompositeBodyFat() {
        Long count = AppleHealthReader.read(CONSOLIDATED_FILE)
                .stream()
                .filter(r -> r.getType().equals("HKQuantityTypeIdentifierBodyFatPercentage"))
                .count();
        assertEquals(2, count);
    }

    @Test
    void readCompositeBodyMass() {
        Long count = AppleHealthReader.read(CONSOLIDATED_FILE)
                .stream()
                .filter(r -> r.getType().equals("HKQuantityTypeIdentifierBodyMass"))
                .count();
        assertEquals(3, count);
    }

    @Test
    void readCompositeDefault() {
        Collection<AppleHealthRecord> records = AppleHealthReader.read(CONSOLIDATED_FILE);
        assertEquals(10, records.size());
    }

    @Test
    void testWriteToFile() throws IOException {
        final File expected = new File(getTestArtifact("apple-export.csv"));
        final File actual = File.createTempFile("test-apple-export", ".csv");
        actual.deleteOnExit();
        Collection<AppleHealthRecord> records = AppleHealthReader.read(CONSOLIDATED_FILE);
        AppleHealthReader.writeToFile(actual.getPath(), records);
        assertEquals(FileUtils.readLines(expected, "UTF-8"), FileUtils.readLines(actual, "UTF-8"));
    }

    @Test
    void testMain() throws IOException {
        final File actual = File.createTempFile("test-apple-export", ".csv");
        final File expected = new File(getTestArtifact("apple-export.csv"));
        String[] args = {CONSOLIDATED_FILE, actual.getPath()};
        AppleHealthReader.main(args);
        assertEquals(FileUtils.readLines(expected, "UTF-8"), FileUtils.readLines(actual, "UTF-8"));
    }

    @Test
    void testMainLogOnly() {
        String[] args = {CONSOLIDATED_FILE};
        AppleHealthReader.main(args);
        assertTrue(true);
    }
}