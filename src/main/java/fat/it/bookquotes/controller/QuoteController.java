package fat.it.bookquotes.controller;

import fat.it.bookquotes.model.Quote;
import fat.it.bookquotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @PostConstruct
    public void fillDB(){
        if(quoteRepository.count()==0){
            quoteRepository.save(new Quote("Sales people are like actors","Finance", "687468434567"));
            quoteRepository.save(new Quote("Sales people are like actors", "Finance", "687468434567"));
        }

        //System.out.println("Reviews test: " + quoteRepository.findReviewsByISBN("687468435454").size());
    }

    @GetMapping("/random")
    public String getRandomQuote(){
        return "Sales people are like actors";
    }

    @GetMapping("/quote/{ISBN}")
    public List<Quote> getQuotesbyISBN(@PathVariable String ISBN){
        return quoteRepository.findQuoteByISBN(ISBN);
    }

    @GetMapping("/category/{category}")
    public List<Quote> getQuotesByCategory(@PathVariable String category){
        return quoteRepository.findQuoteByCategory(category);
    }

    @PostMapping("/quote")
    public Quote addQuote(@RequestBody Quote quote){
        quoteRepository.save(quote);
        return quote;
    }
}
