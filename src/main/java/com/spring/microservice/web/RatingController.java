package com.spring.microservice.web;

import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.TourRatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("API to interact with tour ratings")
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
    @ApiOperation("Find All Ratings")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Rating not found")})
    public List<TourRating> getAll() {
        return tourRatingService.lookupAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find rating by id")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public RatingDto getRating(@PathVariable("id") Integer id) {
        return ratingAssembler.toResource(
                tourRatingService.lookupRatingById(id)
                        .orElseThrow(() -> new NoSuchElementException(String.format(RATING_NOT_FOUND_MESSAGE, id))
                        )
        );
    }
}