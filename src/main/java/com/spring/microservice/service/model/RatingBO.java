package com.spring.microservice.service.model;

import com.spring.microservice.domain.Tour;

/**
 * Rating BO.
 *
 * @author Alexander Huba
 */
public class RatingBO {

    private Tour tour;
    private int customerId;
    private int score;
    private String comment;

    public RatingBO(Tour tour, int customerId, int score, String comment) {
        this.tour = tour;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}