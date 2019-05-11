package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleHealthReaderTest {

    private String bodyFatFile = getTestArtifact("./apple-body-fat-percent.xml");

    static  String getTestArtifact(String filename){
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filename)).getPath();
    }

    @Test
    void readBodyFat() {
        Collection<AppleHealthRecord> records = AppleHealthReader.read(bodyFatFile);
        assertEquals(2, records.size());
    }
}