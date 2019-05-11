package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppleHealthRecordTest {

    private AppleHealthRecord record = new AppleHealthRecord("2019-05-11 06:25:11 -0400", "123.45");

    @Test
    void testToCsv() {
        assertEquals("2019-05-11T06:25:11-04:00,123.45", record.toCsv());
    }

    @Test
    void testToString() {
        assertEquals("AppleHealthRecord{timeCreated=2019-05-11T06:25:11-04:00, value=123.45}", record.toString());
    }
}