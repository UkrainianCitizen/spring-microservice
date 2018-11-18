package com.spring.microservice.web;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tour Rating Controller Unit Test.
 *
 * @author Alexander Huba
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TourRatingController.class)
public class TourRatingControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TourRatingService tourRatingService;
//
//    @MockBean
//    private TourService tourService;
//
//    @InjectMocks
//    private TourRatingController tourRatingController;
//
//    private static final int TOUR_ID = 0;
//    private static final String RATING_DTO_JSON = "{\n" +
//            "  \"score\" : 5,\n" +
//            "  \"comment\" : \"comment\",\n" +
//            "  \"customer_id\" : \"1\"\n" +
//            "}";
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void createTourRatingTest() throws Exception {
//        when(tourService.verifyTour(TOUR_ID)).thenReturn(mock(Tour.class));
//        doNothing().when(tourRatingService).save(any(RatingBO.class));
//
//        mockMvc.perform(
//                post("/tours/{TOUR_ID}/ratings", TOUR_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(RATING_DTO_JSON)
//        )
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        verify(tourService).verifyTour(TOUR_ID);
//        verify(tourRatingService).save(any(RatingBO.class));
//    }
}
