package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    void testCreateFromAttributes() {
        Attributes attributes = mock(Attributes.class);
        when(attributes.getValue("type")).thenReturn("HKQuantityTypeIdentifierBodyFatPercentage");
        when(attributes.getValue("unit")).thenReturn("%");
        when(attributes.getValue("startDate")).thenReturn("2019-05-11 06:25:11 -0400");
        when(attributes.getValue("value")).thenReturn("123.45");

        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertEquals(record, actual);
    }
}