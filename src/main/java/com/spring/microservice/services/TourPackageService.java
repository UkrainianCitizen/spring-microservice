package com.spring.microservice.services;

import com.spring.microservice.domain.TourPackage;

public interface TourPackageService {

    TourPackage createTourPackage(String code, String name);

    Iterable<TourPackage> lookup();

    long total();
}
