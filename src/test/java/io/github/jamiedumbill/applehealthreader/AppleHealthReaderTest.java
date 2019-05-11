package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleHealthReaderTest {

    static String getTestArtifact(String filename){
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename)).getPath();
    }

    @Test
    void readBodyFat() {
        final String bodyFatFile = getTestArtifact("./apple-body-fat-percent.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyFatFile, AppleHealthReader.pickHandler("bf"));
        assertEquals(2, records.size());
    }

    @Test
    void readBodyMass() {
        final String bodyMassFile = getTestArtifact("./apple-body-mass.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyMassFile, AppleHealthReader.pickHandler("bm"));
        assertEquals(3, records.size());
    }

    @Test
    void readHeartRate() {
        final String heartRateFile = getTestArtifact("./apple-heart-rate.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(heartRateFile, AppleHealthReader.pickHandler("hr"));
        assertEquals(5, records.size());
    }

    @Test
    void readCompositeBodyFat() {
        final String bodyFatFile = getTestArtifact("./apple-export.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyFatFile, AppleHealthReader.pickHandler("bf"));
        assertEquals(2, records.size());
    }

    @Test
    void readCompositeBodyMass() {
        final String bodyMassFile = getTestArtifact("./apple-export.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyMassFile, AppleHealthReader.pickHandler("bm"));
        assertEquals(3, records.size());
    }

    @Test
    void readCompositeHeartRate() {
        final String heartRateFile = getTestArtifact("./apple-export.xml");
        Collection<AppleHealthRecord> records = AppleHealthReader.read(heartRateFile, AppleHealthReader.pickHandler("hr"));
        assertEquals(5, records.size());
    }
}