package com.spring.microservice.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object for Rating a Tour.
 */
public class RatingDto extends ResourceSupport {

    @Min(0)
    @Max(5)
    private int score;

    @Size(max = 255)
    private String comment;

    @NotNull
    @JsonProperty("customer_id")
    private int customerId;

    /**
     * Constructor to fully initialize the RatingDto
     *
     * @param score      score
     * @param comment    comment
     * @param customerId customer id
     */
    public RatingDto(int score, String comment, int customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }

    public RatingDto() {
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}