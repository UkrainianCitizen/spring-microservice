package com.spring.microservice.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

/**
 * Region converter test.
 */
@SpringBootTest(webEnvironment = NONE)
public class RegionConverterTest {

    private static final RegionConverter converter = new RegionConverter();

    @Test
    public void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(Region.Central_Coast), is(Region.Central_Coast.getLabel()));
    }

    @Test
    public void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute(Region.Central_Coast.getLabel()), is(Region.Central_Coast));
    }
}