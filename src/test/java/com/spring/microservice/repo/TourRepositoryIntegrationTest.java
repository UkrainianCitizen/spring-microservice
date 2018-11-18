package com.spring.microservice.repo;

import com.spring.microservice.domain.Tour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tour Repository Integration Test.
 *
 * @author Alexander Huba
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TourRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TourRepository tourRepository;

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
