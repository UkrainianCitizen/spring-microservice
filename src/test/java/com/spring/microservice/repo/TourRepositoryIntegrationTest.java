package com.spring.microservice.repo;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.service.TourPackageService;
import com.spring.microservice.service.TourService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TourRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TourRepository tourRepository;

    @MockBean
    private TourPackageService tourPackageService;

    @MockBean
    private TourService tourService;

    private static final String TITLE = "title";

    @Test
    public void findByTitle() {
        // Given
        Tour insertTour = new Tour();
        insertTour.setTitle(TITLE);
        entityManager.persist(insertTour);

        // When
        Tour retrievedTour = tourRepository.findByTitle(TITLE);

        // Then
        assertThat(retrievedTour.getTitle(), is(equalTo(TITLE)));
    }

}