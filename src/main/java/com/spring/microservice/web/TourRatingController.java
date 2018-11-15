package com.spring.microservice.web;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.TourRatingService;
import com.spring.microservice.service.TourService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Tour Rating Controller
 */
@RestController
@Validated
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    private TourRatingService tourRatingService;
    private TourService tourService;

    @Autowired
    public TourRatingController(TourRatingService tourRatingService, TourService tourService) {
        this.tourRatingService = tourRatingService;
        this.tourService = tourService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Valid RatingDto ratingDto) {

        Tour tour = tourService.verifyTour(tourId);
        tourRatingService.save(toRatingBO(tour, ratingDto));
    }

    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable) {

        tourService.verifyTour(tourId);

        Page<TourRating> tourRatingPage = tourRatingService.findByPkTourId(tourId, pageable);
        List<RatingDto> ratingDtoList = tourRatingPage.getContent()
                .stream().map(this::toDto).collect(Collectors.toList());

        return new PageImpl<>(ratingDtoList, pageable, tourRatingPage.getTotalPages());

    }

    @GetMapping(path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {

        tourService.verifyTour(tourId);

        List<TourRating> tourRatings = tourRatingService.findByPkTourId(tourId);
        OptionalDouble average = tourRatings.stream().mapToInt(TourRating::getScore).average();

        return new AbstractMap.SimpleEntry<>("average",
                average.isPresent() ? average.getAsDouble() : null);

    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId,
                                   @RequestBody @Valid RatingDto ratingDto) {

        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        tourRating.setScore(ratingDto.getScore());
        tourRating.setComment(ratingDto.getComment());

        return toDto(tourRatingService.save(tourRating));

    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId,
                                     @RequestBody @Valid RatingDto ratingDto) {

        TourRating tourRating = verifyTourRating(tourId, ratingDto.getCustomerId());

        if (ratingDto.getScore() != 0)
            tourRating.setScore(ratingDto.getScore());
        if (ratingDto.getComment() != null)
            tourRating.setComment(ratingDto.getComment());

        return toDto(tourRatingService.save(tourRating));

    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId,
                       @PathVariable(value = "customerId") int customerId) {

        TourRating tourRating = verifyTourRating(tourId, customerId);

        tourRatingService.delete(tourRating);

    }

    /**
     * Converts the TourRating entity to a RatingDto
     *
     * @param tourRating tour rating
     * @return RatingDto
     */
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
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

    /**
     * Verifies and returns the TourRating for a particular tourId and Customer
     *
     * @param tourId     tour id
     * @param customerId customer id
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {

        TourRating rating = tourRatingService.findByPkTourIdAndPkCustomerId(tourId, customerId);

        if (rating == null) {
            throw new NoSuchElementException("Tour-Rating pair for request("
                    + tourId + " for customer" + customerId);

        }

        return rating;
    }
}