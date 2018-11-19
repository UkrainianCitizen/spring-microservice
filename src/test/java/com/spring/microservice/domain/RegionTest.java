package com.spring.microservice.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Region test.
 */
@SpringBootTest(webEnvironment = NONE)
public class RegionTest {

    @Test
    public void findByLabel() {
        assertThat(Region.Central_Coast, is(Region.findByLabel("Central Coast")));
        assertThat(Region.Northern_california, is(Region.findByLabel("Northern California")));
        assertThat(Region.Southern_California, is(Region.findByLabel("Southern California")));
        assertThat(Region.Varies, is(Region.findByLabel("Varies")));
    }

    @Test
    public void getLabel() {
        assertThat(Region.Central_Coast.getLabel(), is("Central Coast"));
        assertThat(Region.Northern_california.getLabel(), is("Northern California"));
        assertThat(Region.Southern_California.getLabel(), is("Southern California"));
        assertThat(Region.Varies.getLabel(), is("Varies"));
    }
}