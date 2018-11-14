package com.spring.microservice.service;

import com.spring.microservice.domain.TourPackage;
import com.spring.microservice.repo.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tour Package Service.
 */
@Service
public class TourPackageServiceImpl implements TourPackageService {

    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageServiceImpl(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public TourPackage createTourPackage(String code, String name) {

        if (!tourPackageRepository.existsById(code)) {
            return tourPackageRepository.save(new TourPackage(code, name));
        } else {
            return null;
        }
    }

    @Override
    public Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    @Override
    public long total() {
        return tourPackageRepository.count();
    }
}