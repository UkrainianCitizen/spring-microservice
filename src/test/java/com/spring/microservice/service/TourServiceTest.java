package com.spring.microservice.service;

import com.spring.microservice.domain.Difficulty;
import com.spring.microservice.domain.Region;
import com.spring.microservice.domain.Tour;
import com.spring.microservice.domain.TourPackage;
import com.spring.microservice.repo.TourPackageRepository;
import com.spring.microservice.repo.TourRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tour Service Unit Test.
 *
 * @author Alexander Huba
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TourServiceTest {

    @Mock
    private TourPackageRepository tourPackageRepository;

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourService tourService = new TourServiceImpl(tourPackageRepository, tourRepository);

    private static String TITLE = "title";
    private static String DESCRIPTION = "description";
    private static String BLURB = "blurb";
    private static Integer PRICE = 111;
    private static String DURATION = "duration";
    private static String BULLETS = "blurb";
    private static String KEYWORDS = "keywords";
    private static String TOUR_PACKAGE_NAME = "Cycle California";
    private static Difficulty DIFFICULTY = Difficulty.Varies;
    private static Region REGION = Region.Varies;
    private static TourPackage TOUR_PACKAGE = new TourPackage("CY", TOUR_PACKAGE_NAME);
    private static Tour TOUR = new Tour(TITLE, DESCRIPTION, BLURB, PRICE, DURATION, BULLETS, KEYWORDS,
            TOUR_PACKAGE, DIFFICULTY, REGION);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTourTest() {

        when(tourPackageRepository.findByName(TOUR_PACKAGE_NAME)).thenReturn(Optional.of(TOUR_PACKAGE));
        when(tourRepository.save(argThat(new TourArgumentMatcher()))).thenReturn(TOUR);

        Tour tour = tourService.createTour(TITLE, DESCRIPTION, BLURB, PRICE, DURATION, BULLETS, KEYWORDS,
                TOUR_PACKAGE_NAME, DIFFICULTY, REGION);

        assertEquals(tour, TOUR);

        verify(tourPackageRepository).findByName(TOUR_PACKAGE_NAME);
        verify(tourRepository).save(argThat(new TourArgumentMatcher()));
        verifyNoMoreInteractions(tourPackageRepository, tourRepository);

    }

    private static class TourArgumentMatcher implements ArgumentMatcher<Tour> {
        @Override
        public boolean matches(Tour tour) {
            return tour.getTitle().equals(TITLE) &&
                    tour.getDescription().equals(DESCRIPTION) &&
                    tour.getBlurb().equals(BLURB) &&
                    tour.getPrice().equals(PRICE) &&
                    tour.getDuration().equals(DURATION) &&
                    tour.getBullets().equals(BULLETS) &&
                    tour.getKeywords().equals(KEYWORDS) &&
                    tour.getTourPackage().getName().equals(TOUR_PACKAGE_NAME) &&
                    tour.getDifficulty().equals(DIFFICULTY) &&
                    tour.getRegion().equals(REGION);
        }
    }
}
