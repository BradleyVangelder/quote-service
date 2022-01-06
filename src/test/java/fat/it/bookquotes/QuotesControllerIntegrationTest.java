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
    public void givenQuotes_whenGetQuotesByISBN_thenReturnJsonReviews() throws Exception {

        List<Quote> quoteList = new ArrayList<>();
        quoteList.add(quote1);
        quoteList.add(quote2);

        mockMvc.perform(get("/quote/book/{isbn}", "6874684345634"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isbn", is("6874684345634")))
                .andExpect(jsonPath("$[0].quote", is("Competition is for losers")))
                .andExpect(jsonPath("$[1].isbn", is("6874684345634")))
                .andExpect(jsonPath("$[1].quote", is("Sales people are like actors")));
    }

    @Test
    public void whenPostQuote_thenReturnJsonReview() throws Exception {
        Quote quoteBook1 = new Quote("nieuwequote", "ISBN1");

        mockMvc.perform(post("/quote")
                .content(mapper.writeValueAsString(quoteBook1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.quote", is("nieuwequote")));
    }

//    @Test
//    public void givenQuote_whenPutQuote_thenReturnJsonReview() throws Exception {
//
//        Quote updatedQuote = new Quote("nieuwequote", "ISBN1");
//
//        mockMvc.perform(put("/quote/{id}",1)
//                .content(mapper.writeValueAsString(updatedQuote))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isbn", is("ISBN1")))
//                .andExpect(jsonPath("$.quote", is("nieuwequote")));
//    }

    @Test
    public void givenQuote_whenDeleteQuote_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/quote/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
