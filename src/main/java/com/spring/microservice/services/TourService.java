package com.spring.microservice.services;

import com.spring.microservice.domain.Difficulty;
import com.spring.microservice.domain.Region;
import com.spring.microservice.domain.Tour;

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
}
