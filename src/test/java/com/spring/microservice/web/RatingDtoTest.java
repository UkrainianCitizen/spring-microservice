package com.spring.microservice.web;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Rating dto test.
 */
public class RatingDtoTest {

    @Test
    public void testConstructor() {
        RatingDto dto = new RatingDto(1, "comment", 2);
        assertThat(dto.getScore(), is(1));
        assertThat(dto.getComment(), is("comment"));
        assertThat(dto.getCustomerId(), is(2));
    }

    @Test
    public void testSetters() {
        RatingDto dto = new RatingDto();
        dto.setComment("comment");
        dto.setCustomerId(2);
        dto.setScore(1);
        assertThat(dto.getScore(), is(1));
        assertThat(dto.getComment(), is("comment"));
        assertThat(dto.getCustomerId(), is(2));
    }
}