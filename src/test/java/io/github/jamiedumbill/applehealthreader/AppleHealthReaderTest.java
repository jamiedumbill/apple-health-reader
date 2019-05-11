package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleHealthReaderTest {

    private String filePath =  Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("./apple-body-fat-percent.xml")).getPath();

    @Test
    void read() {
        Collection<AppleHealthRecord> records = AppleHealthReader.read(filePath);
        assertEquals(2, records.size());
    }
}