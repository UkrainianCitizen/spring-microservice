package com.spring.microservice.domain;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Tour rating test.
 */
@SpringBootTest(webEnvironment = NONE)
public class TourRatingTest {

    private static final Tour tour = new Tour(
            "title",
            "description",
            "blurb",
            50,
            "1 day",
            "bullet",
            "keywords",
            new TourPackage("CC","name"),
            Difficulty.Difficult,
            Region.Central_Coast);

    @Test
    public void testConstructor1() {
        TourRating rating = new TourRating(tour, 1, 1, "comment");
        testIt(rating);
        assertThat(rating.getComment(), is("comment"));
    }
    @Test
    public void testConstructor2() {
        TourRating rating = new TourRating(tour, 1, 1);
        testIt(rating);
        assertThat(rating.getComment(), is("Terrible"));
    }

    private void testIt(TourRating rating){
        assertThat(rating.getId(), is(nullValue()));
        assertThat(rating.getTour(), is(tour));
        assertThat(rating.getScore(), is(1));
        assertThat(rating.getCustomerId(), is(1));
    }
}