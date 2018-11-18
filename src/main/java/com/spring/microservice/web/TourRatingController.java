package com.spring.microservice.web;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.TourRatingService;
import com.spring.microservice.service.model.RatingBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tour Rating Controller
 */
@RestController
@Validated
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    private TourRatingService tourRatingService;

    @Autowired
    public TourRatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    /**
     * Create a Tour Rating.
     *
     * @param tourId    tour id
     * @param ratingDto rating dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
    }

    /**
     * Create Several Tour Ratings for one tour, score and several customers.
     *
     * @param tourId    tour id
     * @param score     score
     * @param customers customers
     */
    @PostMapping("/{score}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyTourRatings(@PathVariable(value = "tourId") int tourId,
                                      @PathVariable(value = "score") int score,
                                      @RequestParam("customers") Integer customers[]) {
        tourRatingService.rateMany(tourId, score, customers);
    }

    /**
     * Lookup the Ratings for a tour.
     *
     * @param tourId   tour id
     * @param pageable pageable object
     * @return ratings for the given tour
     */
    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        Page<TourRating> tourRatingPage = tourRatingService.lookupRatings(tourId, pageable);
        List<RatingDto> ratingDtoList = tourRatingPage.getContent()
                .stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
    }

    /**
     * Calculate the average Score of a Tour.
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
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.update(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Update score or comment of a Tour Rating
     *
     * @param tourId    tour id
     * @param ratingDto rating dto
     * @return The modified Rating DTO.
     */
    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        return toDto(tourRatingService.updateSome(tourId, ratingDto.getCustomerId(),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Delete a Rating of a tour made by a customer
     *
     * @param tourId     tour id
     * @param customerId customer id
     */
    @DeleteMapping("/{customerId}")
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