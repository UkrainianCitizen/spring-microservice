package com.spring.microservice.services;

import com.spring.microservice.domain.TourPackage;
import com.spring.microservice.repo.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tour Package Service.
 */
@Service
public class TourPackageService {

    @Autowired
    private TourPackageRepository tourPackageRepository;

    public TourPackage createTourPackage(String code, String name) {

        if (!tourPackageRepository.existsById(code)) {
            return tourPackageRepository.save(new TourPackage(code, name));
        } else {
            return null;
        }
    }

    public Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    public long total() {
        return tourPackageRepository.count();
    }

}
