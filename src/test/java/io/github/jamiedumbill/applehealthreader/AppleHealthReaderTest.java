package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleHealthReaderTest {

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
        final String bodyFatFile = getTestArtifact("./apple-export.xml");
        Long count = AppleHealthReader.read(bodyFatFile)
                .stream()
                .filter(r-> r.getType().equals("HKQuantityTypeIdentifierBodyFatPercentage"))
                .count();
        assertEquals(2, count);
    }

    @Test
    void readCompositeBodyMass() {
        final String bodyMassFile = getTestArtifact("./apple-export.xml");
        Long count = AppleHealthReader.read(bodyMassFile)
                .stream()
                .filter(r-> r.getType().equals("HKQuantityTypeIdentifierBodyMass"))
                .count();
        assertEquals(3, count);
    }

    @Test
    void readCompositeDefault() {
        final String heartRateFile = getTestArtifact("./apple-export.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(heartRateFile);
        assertEquals(10, records.size());
    }
}