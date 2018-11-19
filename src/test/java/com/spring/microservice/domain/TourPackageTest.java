package com.spring.microservice.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Tour package test.
 */
@SpringBootTest(webEnvironment = NONE)
public class TourPackageTest {

    @Test
    public void testConstructorAndGetters() throws Exception {
        TourPackage p = new TourPackage("CC","name");
        assertThat(p.getName(), is("name"));
        assertThat(p.getCode(), is("CC"));
    }
}