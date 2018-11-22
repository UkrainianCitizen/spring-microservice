package com.spring.microservice.web;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.TourRatingService;
import com.spring.microservice.service.dto.RatingBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;

/**
 * Tour Rating Controller.
 */
@RestController
@Validated
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    private TourRatingService tourRatingService;
    private RatingAssembler assembler;

    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingController.class);

    @Autowired
    public TourRatingController(TourRatingService tourRatingService, RatingAssembler assembler) {
        this.tourRatingService = tourRatingService;
        this.assembler = assembler;
    }

    /**
     * Creates a Tour Rating.
     *
     * @param tourId    tour id
     * @param ratingDto rating dto
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    /**
     * Creates Several Tour Ratings for one tour, score and several customers.
     *
     * @param tourId    tour id
     * @param score     score
     * @param customers customers
     */
    @PostMapping("/{score}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam("customers") Integer customers[]) {
        LOGGER.info("POST /tours/{}/ratings/{}", tourId, score);
        tourRatingService.rateMany(tourId, score, customers);
    }

    /**
     * Lookups the Ratings for a tour.
     *
     * @param tourId                  tour id
     * @param pageable                pageable object
     * @param pagedResourcesAssembler paged resources assembler
     * @return HATEOAS page with ratings for the given tour
     */
    @GetMapping
    public PagedResources getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable, PagedResourcesAssembler<TourRating> pagedResourcesAssembler) {
        Page<TourRating> tourRatingPage = tourRatingService.lookupRatings(tourId, pageable);
        return pagedResourcesAssembler.toResource(tourRatingPage, assembler);
    }

    /**
     * Calculates the average Score of a Tour.
     *
     * @param tourId tour id
     * @return Tuple of "average" and the average value.
     */
    @GetMapping("/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        return new AbstractMap.SimpleEntry<>("average", tourRatingService.getAverageScore(tourId));
    }

    /**
     * Update score and comment of a Tour Rating
     *
     * @param tourId    tour id
     * @param ratingDto rating dto
     * @return The modified Rating DTO.
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Updates a score or a comment of a Tour Rating
     *
     * @param tourId    tour id
     * @param ratingDto rating dto
     * @return The modified Rating DTO.
     */
    @PatchMapping
    @PreAuthorize("hasRole('ROLE_CSR')")
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Deletes a Rating of a tour made by a customer
     *
     * @param tourId     tour id
     * @param customerId customer id
     */
    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        tourRatingService.delete(tourId, customerId);
    }

    /**
     * Converts the TourRating entity to a RatingDto
     *
     * @param tourRating tour rating
     * @return RatingDto
     */
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getCustomerId());
    }

    /**
     * Converts RatingDto to RatingBo.
     *
     * @param tour      tour
     * @param ratingDto ratingDto
     * @return rating BO
     */
    private RatingBO toRatingBO(Tour tour, RatingDto ratingDto) {
        return new RatingBO(tour, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }
}