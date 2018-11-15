package com.spring.microservice.service;

import com.spring.microservice.domain.TourRating;
import com.spring.microservice.domain.TourRatingPk;
import com.spring.microservice.repo.TourRatingRepository;
import com.spring.microservice.service.model.RatingBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourRatingServiceImpl implements TourRatingService {

    private TourRatingRepository tourRatingRepository;

    @Autowired
    public TourRatingServiceImpl(TourRatingRepository tourRatingRepository) {
        this.tourRatingRepository = tourRatingRepository;
    }

    @Override
    public List<TourRating> findByPkTourId(Integer tourId) {
        return tourRatingRepository.findByPkTourId(tourId);
    }

    @Override
    public Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable) {
        return tourRatingRepository.findByPkTourId(tourId, pageable);
    }

    @Override
    public TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
    }

    @Override
    public void save(RatingBO ratingBO) {
        tourRatingRepository.save(new TourRating(new TourRatingPk(ratingBO.getTour(), ratingBO.getCustomerId()),
                ratingBO.getScore(), ratingBO.getComment()));
    }

    @Override
    public TourRating save(TourRating tourRating) {
        return tourRatingRepository.save(tourRating);
    }

    @Override
    public void delete(TourRating tourRating) {
        tourRatingRepository.delete(tourRating);
    }
}
