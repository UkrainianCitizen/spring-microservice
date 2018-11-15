package com.spring.microservice.service;

import com.spring.microservice.domain.TourRating;
import com.spring.microservice.service.model.RatingBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TourRatingService {

    List<TourRating> findByPkTourId(Integer tourId);

    Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);

    TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);

    void save(RatingBO ratingBO);

    TourRating save(TourRating tourRating);

    void delete(TourRating tourRating);
}
