package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppleHealthRecordTest {

    private AppleHealthRecord record = new AppleHealthRecord("HKQuantityTypeIdentifierBodyFatPercentage", "%", "2019-05-11 06:25:11 -0400", "123.45");

    @Test
    void testToCsv() {
        assertEquals("HKQuantityTypeIdentifierBodyFatPercentage,%,2019-05-11T06:25:11-04:00,123.45", record.toCsv());
    }

    @Test
    void testToString() {
        assertEquals("AppleHealthRecord{type='HKQuantityTypeIdentifierBodyFatPercentage', unit='%', timeCreated=2019-05-11T06:25:11-04:00, value=123.45}", record.toString());
    }
}