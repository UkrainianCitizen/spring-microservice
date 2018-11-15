package com.spring.microservice.web;

import com.spring.microservice.domain.Tour;
import com.spring.microservice.service.TourPackageService;
import com.spring.microservice.service.TourRatingService;
import com.spring.microservice.service.TourService;
import com.spring.microservice.service.model.RatingBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TourRatingController.class)
public class TourRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourRatingService tourRatingService;

    @MockBean
    private TourService tourService;

    @MockBean
    private TourPackageService tourPackageService;

    @InjectMocks
    private TourRatingController tourRatingController = new TourRatingController(tourRatingService, tourService);

    private static final int TOUR_ID = 0;
    private static final String RATING_DTO_JSON = "{\n" +
            "  \"score\" : 5,\n" +
            "  \"comment\" : \"comment\",\n" +
            "  \"customer_id\" : \"1\"\n" +
            "}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTourTest() throws Exception {
        when(tourService.verifyTour(TOUR_ID)).thenReturn(mock(Tour.class));
        doNothing().when(tourRatingService).save(any(RatingBO.class));

        mockMvc.perform(
                post("/tours/{TOUR_ID}/ratings", TOUR_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RATING_DTO_JSON)
        )
                .andExpect(status().isCreated())
                .andReturn();

        verify(tourService).verifyTour(TOUR_ID);
        verify(tourRatingService).save(any(RatingBO.class));
    }
}
