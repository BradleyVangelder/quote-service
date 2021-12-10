package fat.it.bookquotes;

//import com.sun.org.apache.xpath.internal.operations.Quo;
import fat.it.bookquotes.model.Quote;
import fat.it.bookquotes.repository.QuoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuoteControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteRepository quoteRepository;

    private Quote quote1 = new Quote("Sales people are like actors", "6874684345634");
    private Quote quote2 = new Quote("Competition is for losers", "6874684345634");
    private Quote quote3 = new Quote("test", "623468434567");
    private Quote quote4 = new Quote("Testing is like testing", "6874682344564");

    @BeforeEach
    public void beforeAllTests() {
        quoteRepository.deleteAll();
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
        quoteRepository.save(quote4);
        quoteRepository.save(quote1);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        quoteRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenReview_whenGetReviewsByISBN_thenReturnJsonReviews() throws Exception {

        List<Quote> reviewList = new ArrayList<>();
        reviewList.add(quote1);
        reviewList.add(quote2);

        mockMvc.perform(get("/quote/book/{isbn}", "6874684345634"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isbn", is("6874684345634")))
                .andExpect(jsonPath("$[0].quote", is("Competition is for losers")))
                .andExpect(jsonPath("$[1].isbn", is("6874684345634")))
                .andExpect(jsonPath("$[1].quote", is("Sales people are like actors")));
    }

//    @Test
//    public void givenReview_whenGetReviewsByUserId_thenReturnJsonReviews() throws Exception {
//
//        List<Review> reviewList = new ArrayList<>();
//        reviewList.add(reviewUser1Book1);
//        reviewList.add(reviewUser1Book2);
//
//        mockMvc.perform(get("/reviews/user/{userId}", 001))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].userId", is(001)))
//                .andExpect(jsonPath("$[0].isbn", is("ISBN1")))
//                .andExpect(jsonPath("$[0].scoreNumber", is(1)))
//                .andExpect(jsonPath("$[1].userId", is(001)))
//                .andExpect(jsonPath("$[1].isbn", is("ISBN2")))
//                .andExpect(jsonPath("$[1].scoreNumber", is(2)));
//    }
//
//    @Test
//    public void whenPostReview_thenReturnJsonReview() throws Exception {
//        Review reviewUser3Book1 = new Review(003, "ISBN1", 1);
//
//        mockMvc.perform(post("/reviews")
//                .content(mapper.writeValueAsString(reviewUser3Book1))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId", is(003)))
//                .andExpect(jsonPath("$.isbn", is("ISBN1")))
//                .andExpect(jsonPath("$.scoreNumber", is(1)));
//    }
//
//    @Test
//    public void givenReview_whenPutReview_thenReturnJsonReview() throws Exception {
//
//        Review updatedReview = new Review(001, "ISBN1", 2);
//
//        mockMvc.perform(put("/reviews")
//                .content(mapper.writeValueAsString(updatedReview))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId", is(001)))
//                .andExpect(jsonPath("$.isbn", is("ISBN1")))
//                .andExpect(jsonPath("$.scoreNumber", is(2)));
//    }
//
//    @Test
//    public void givenReview_whenDeleteReview_thenStatusOk() throws Exception {
//
//        mockMvc.perform(delete("/reviews/user/{userId}/book/{ISBN}", 999, "ISBN9")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void givenNoReview_whenDeleteReview_thenStatusNotFound() throws Exception {
//
//        mockMvc.perform(delete("/reviews/user/{userId}/book/{ISBN}", 888, "ISBN8")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

}
