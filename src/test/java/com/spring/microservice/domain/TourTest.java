package com.spring.microservice.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Tour test.
 */
@SpringBootTest(webEnvironment = NONE)
public class TourTest {

    @Test
    public void testConstructorAndGetters() {
        TourPackage p = new TourPackage("CC","name");
        Tour tour = new Tour("title","description","blurb", 50, "1 day", "bullet",
                "keywords", p, Difficulty.Difficult, Region.Central_Coast);
        assertNull(tour.getId());
        assertThat(tour.getTitle(), is("title"));
        assertThat(tour.getDescription(), is("description"));
        assertThat(tour.getBlurb(), is("blurb"));
        assertThat(tour.getPrice(), is(50));
        assertThat(tour.getDuration(), is("1 day"));
        assertThat(tour.getBullets(), is("bullet"));
        assertThat(tour.getKeywords(), is("keywords"));
        assertThat(tour.getTourPackage().getCode(), is("CC"));
        assertThat(tour.getDifficulty(), is(Difficulty.Difficult));
        assertThat(tour.getRegion(), is(Region.Central_Coast));
    }
}