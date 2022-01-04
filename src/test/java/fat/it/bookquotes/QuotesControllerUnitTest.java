package fat.it.bookquotes;

import fat.it.bookquotes.model.Quote;
import fat.it.bookquotes.repository.QuoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class QuotesControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteRepository quoteRepository;

    private Quote quote1 = new Quote("Quote1", "ISBN1");
    private Quote quote2 = new Quote("Quote2", "ISBN2");

    private List<Quote> allQuotes = Arrays.asList(quote1, quote2);
    @BeforeEach
    public void beforeAllTests() {
        quoteRepository.deleteAll();
        quoteRepository.save(quote1);
        quoteRepository.save(quote2);
    }
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenQuote_whenGetQuoteByID_thenReturnJsonQuote() throws Exception {

        given(quoteRepository.findQuoteById("1")).willReturn(quote1);

        mockMvc.perform(get("/quote/{id}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote",is("Quote1")))
                .andExpect(jsonPath("$.isbn",is("ISBN1")));
    }

    @Test
    public void givenQuote_whenGetQuoteByISBN_thenReturnJsonQuote() throws Exception {

        given(quoteRepository.findQuoteByISBN("ISBN1")).willReturn(Collections.singletonList(quote1));

        mockMvc.perform(get("/quote/book/{ISBN}","ISBN1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quote",is("Quote1")))
                .andExpect(jsonPath("$[0].isbn",is("ISBN1")));
    }
    @Test
    public void whenPostQuote_thenReturnJsonQuote() throws Exception{
        mockMvc.perform(post("/quote")
                .content(mapper.writeValueAsString(quote1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn",is("ISBN1")))
                .andExpect(jsonPath("$.quote",is("Quote1")));
    }

    @Test
    public void givenQuote_whenPutQuote_thenReturnJsonReview() throws Exception{
        given(quoteRepository.findQuoteById("1")).willReturn(quote1);

        Quote updatedQuote = new Quote("nieuwe quote","ISBN1");

        mockMvc.perform(put("/quote/{id}",1)
                .content(mapper.writeValueAsString(updatedQuote))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn",is("ISBN1")))
                .andExpect(jsonPath("$.quote",is("nieuwe quote")));
    }

    @Test
    public void givenQuote_whenDeleteQuote_thenStatusOk() throws Exception{
        given(quoteRepository.findQuoteById("1")).willReturn(quote1);

        mockMvc.perform(delete("/quote/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
