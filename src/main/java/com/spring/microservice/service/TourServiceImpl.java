package com.spring.microservice.service;

import com.spring.microservice.domain.Difficulty;
import com.spring.microservice.domain.Region;
import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourPackage;
import com.spring.microservice.repo.TourPackageRepository;
import com.spring.microservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Tour  Service.
 */
@Service
public class TourServiceImpl implements TourService {

    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    private static final String TOUR_NOT_VERIFIED_MESSAGE = "Tour № %d does not exist.";

    @Autowired
    public TourServiceImpl(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public Tour createTour(String title, String description, String blurb, Integer price, String duration,
                           String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region) {

        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist: " + tourPackageName));
        return tourRepository.save(new Tour(title, description, blurb, price, duration,
                bullets, keywords, tourPackage, difficulty, region));
    }

    @Override
    public Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException(String.format(TOUR_NOT_VERIFIED_MESSAGE, tourId)));
    }

    @Override
    public long total() {
        return tourRepository.count();
    }
}