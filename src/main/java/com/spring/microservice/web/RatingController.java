package com.spring.microservice.web;

import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.TourRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Rating Controller.
 */
@RestController
@RequestMapping(path = "/ratings")
public class RatingController {

    private TourRatingService tourRatingService;
    private RatingAssembler ratingAssembler;

    private static final String RATING_NOT_FOUND_MESSAGE = "Rating %d not found";

    @Autowired
    public RatingController(TourRatingService tourRatingService, RatingAssembler ratingAssembler) {
        this.tourRatingService = tourRatingService;
        this.ratingAssembler = ratingAssembler;
    }

    @GetMapping
    public List<TourRating> getAll() {
        return tourRatingService.lookupAll();
    }

    @GetMapping("/{id}")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        return ratingAssembler.toResource(
                tourRatingService.lookupRatingById(id)
                        .orElseThrow(() -> new NoSuchElementException(String.format(RATING_NOT_FOUND_MESSAGE, id))
                        )
        );
    }
}