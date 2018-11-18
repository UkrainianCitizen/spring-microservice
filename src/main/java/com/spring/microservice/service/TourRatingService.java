package com.spring.microservice.service;

import com.spring.microservice.domain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

/**
 * Tour Rating Service.
 *
 * @author Alexander Huba
 */
public interface TourRatingService {

    /**
     * Create a new Tour Rating in the database
     *
     * @param tourId     tour identifier
     * @param customerId customer identifier
     * @param score      score of the tour rating
     * @param comment    additional comment
     * @throws NoSuchElementException if no Tour found.
     */
    void createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException;

    /**
     * Get a page of tour ratings for a tour.
     *
     * @param tourId   tour identifier
     * @param pageable page parameters to determine which elements to fetch
     * @return Page of TourRatings
     * @throws NoSuchElementException if no Tour found.
     */
    Page<TourRating> lookupRatings(int tourId, Pageable pageable) throws NoSuchElementException;

    /**
     * Update some of the elements of a Tour Rating.
     *
     * @param tourId  tour identifier
     * @param score   score of the tour rating
     * @param comment additional comment
     * @return Tour Rating Domain Object
     * @throws NoSuchElementException if no Tour found.
     */
    TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException;

    /**
     * Update all of the elements of a Tour Rating.
     *
     * @param tourId     tour identifier
     * @param customerId customer identifier
     * @param score      score of the tour rating
     * @param comment    additional comment
     * @return Tour Rating Domain Object
     * @throws NoSuchElementException if no Tour found.
     */
    TourRating updateSome(int tourId, Integer customerId, Integer score, String comment);

    /**
     * Delete a Tour Rating.
     *
     * @param tourId     tour identifier
     * @param customerId customer identifier
     * @throws NoSuchElementException if no Tour found.
     */
    void delete(int tourId, Integer customerId) throws NoSuchElementException;

    /**
     * Get the average score of a tour.
     *
     * @param tourId tour identifier
     * @return average score as a Double.
     * @throws NoSuchElementException if no Tour exists
     */
    Double getAverageScore(int tourId) throws NoSuchElementException;

    /**
     * Service for many customers to give the same score for a service
     *
     * @param tourId    tour identifier
     * @param score     score
     * @param customers list with customers ids
     */
    void rateMany(int tourId, int score, Integer[] customers);
}
