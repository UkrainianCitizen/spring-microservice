package com.spring.microservice.service.impl;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourRating;
import com.spring.microservice.repo.TourRatingRepository;
import com.spring.microservice.repo.TourRepository;
import com.spring.microservice.service.TourRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Tour Rating Service Implementation.
 */
@Service
@Transactional
public class TourRatingServiceImpl implements TourRatingService {

    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TourRatingService.class);

    @Autowired
    public TourRatingServiceImpl(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId,
                score, comment));
    }

    @Override
    public Optional<TourRating> lookupRatingById(int id) {
        return tourRatingRepository.findById(id);
    }

    @Override
    public List<TourRating> lookupAll() {
        return (List<TourRating>) tourRatingRepository.findAll();
    }

    @Override
    public Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException {
        return tourRatingRepository.findByTourId(verifyTour(tourId).getId(), pageable);
    }

    @Override
    public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        rating.setScore(score);
        rating.setComment(comment);
        return tourRatingRepository.save(rating);
    }

    @Override
    public TourRating updateSome(int tourId, Integer customerId, Integer score, String comment)
            throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        if (score != null) {
            rating.setScore(score);
        }
        if (comment != null) {
            rating.setComment(comment);
        }
        return tourRatingRepository.save(rating);
    }

    @Override
    public void delete(int tourId, Integer customerId) throws NoSuchElementException {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    @Override
    public Double getAverageScore(int tourId) throws NoSuchElementException {
        List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
        OptionalDouble average = ratings.stream().mapToInt(TourRating::getScore).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    @Override
    public void rateMany(int tourId, int score, Integer[] customers) {
        LOGGER.info("Rate tour {} by customers {}", tourId, Arrays.asList(customers).toString());
        Tour tour = tourRepository.findById(tourId).orElseThrow(NoSuchElementException::new);
            for (Integer customer : customers) {
                LOGGER.debug("Attempt to create Tour Rating for customer {}", customer);
                tourRatingRepository.save(new TourRating(tour, customer, score));
            }
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId)
        );
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }
}