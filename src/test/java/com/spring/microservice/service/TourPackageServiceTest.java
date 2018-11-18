package com.spring.microservice.service;

import com.spring.microservice.domain.TourPackage;
import com.spring.microservice.repo.TourPackageRepository;
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

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tour Package Service Unit Test.
 *
 * @author Alexander Huba
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TourPackageServiceTest {

    @Mock
    private TourPackageRepository tourPackageRepository;

    @InjectMocks
    private TourPackageService tourPackageService = new TourPackageServiceImpl(tourPackageRepository);

    private static String CODE = "SC";
    private static String NAME = "Snowboard Cali";
    private static TourPackage TOUR_PACKAGE = new TourPackage(CODE, NAME);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTourPackageTest() {

        when(tourPackageRepository.existsById(CODE)).thenReturn(false);
        when(tourPackageRepository.save(argThat(new InsertTourPackageMatcher()))).thenReturn(TOUR_PACKAGE);

        TourPackage resultTourPackage = tourPackageService.createTourPackage(CODE, NAME);

        assertNotNull(resultTourPackage);
        assertEquals(resultTourPackage.getCode(), TOUR_PACKAGE.getCode());
        assertEquals(resultTourPackage.getName(), TOUR_PACKAGE.getName());

        verify(tourPackageRepository).existsById(CODE);
        verify(tourPackageRepository).save(argThat(new InsertTourPackageMatcher()));
        verifyNoMoreInteractions(tourPackageRepository);
    }

    @Test
    public void lookupTest() {
        when(tourPackageRepository.findAll()).thenReturn(Collections.emptyList());

        Iterable result = tourPackageService.lookup();

        assertNotNull(result);
        verify(tourPackageRepository).findAll();
    }

    private static class InsertTourPackageMatcher implements ArgumentMatcher<TourPackage> {
        @Override
        public boolean matches(TourPackage tourPackage) {
            return tourPackage.getCode().equals(CODE) &&
                    tourPackage.getName().equals(NAME);
        }
    }
}
