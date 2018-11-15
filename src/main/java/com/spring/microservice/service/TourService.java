package com.spring.microservice.service;

import com.spring.microservice.domain.Difficulty;
import com.spring.microservice.domain.Region;
import com.spring.microservice.domain.Tour;

import java.util.NoSuchElementException;

public interface TourService {

    /**
     * Create a new Tour Object and persist it to the Database.
     *
     * @param title           title
     * @param description     description
     * @param blurb           blurb
     * @param price           price
     * @param duration        duration
     * @param bullets         bullets
     * @param keywords        keywords
     * @param tourPackageName tourPackageName
     * @param difficulty      difficulty
     * @param region          region
     * @return Tour Entity
     */
    Tour createTour(String title, String description, String blurb, Integer price, String duration,
                    String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region);

    /**
     * Calculate the number of Tours in the Database.
     *
     * @return the total.
     */
    long total();

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId tour id
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    Tour verifyTour(int tourId) throws NoSuchElementException;
}
