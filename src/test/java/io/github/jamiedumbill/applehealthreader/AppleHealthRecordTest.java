package io.github.jamiedumbill.applehealthreader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppleHealthRecordTest {

    private final AppleHealthRecord record = new AppleHealthRecord("HKQuantityTypeIdentifierBodyFatPercentage", "%", "2019-05-11 06:25:11 -0400", "123.45");
    private final Attributes attributes = mock(Attributes.class);

    @BeforeEach
    void setUp() {
        when(attributes.getValue("type")).thenReturn("HKQuantityTypeIdentifierBodyFatPercentage");
        when(attributes.getValue("unit")).thenReturn("%");
        when(attributes.getValue("startDate")).thenReturn("2019-05-11 06:25:11 -0400");
        when(attributes.getValue("value")).thenReturn("123.45");
    }

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
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertEquals(record, actual);
    }

    @Test
    void testEqualsSame() {
        assertEquals(record, record);
    }

    @Test
    void testEquals() {
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertEquals(actual, record);
    }

    @Test
    void testEqualsDifferentType() {
        when(attributes.getValue("type")).thenReturn("HKQuantityTypeIdentifierBodyMass");
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertEquals(actual, record);
    }

    @Test
    void testEqualsDifferentUnit() {
        when(attributes.getValue("unit")).thenReturn("lbs");
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertNotEquals(actual, record);
    }

    @Test
    void testEqualsDifferentStartDate() {
        when(attributes.getValue("startDate")).thenReturn("2018-05-11 06:25:11 -0400");
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertNotEquals(actual, record);
    }

    @Test
    void testEqualsDifferentValue() {
        when(attributes.getValue("value")).thenReturn("54.321");
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertNotEquals(actual, record);
    }

    @Test
    void testHash() {
        AppleHealthRecord actual = AppleHealthRecord.createFromXMLAttributes(attributes);
        assertEquals(record.hashCode(), actual.hashCode());
    }
}