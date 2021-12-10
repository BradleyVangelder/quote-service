package fat.it.bookquotes.controller;

import fat.it.bookquotes.model.Quote;
import fat.it.bookquotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @PostConstruct
    public void fillDB(){
        if(quoteRepository.count()==0){
            quoteRepository.save(new Quote("Sales people are like actors", "687468434568"));
            quoteRepository.save(new Quote("Do it LEAN", "687468434578"));
        }
    }

    @GetMapping("/random")
    public String getRandomQuote(){
        return "Sales people are like actors";
    }

    @GetMapping("/{ISBN}")
    public List<Quote> getQuotesbyISBN(@PathVariable String ISBN){
        return quoteRepository.findQuoteByISBN(ISBN);
    }

    @GetMapping("/{id}")
    public Quote getQuoteById(@PathVariable String id){
        return quoteRepository.findQuoteById(id);
    }

    @PostMapping()
    public Quote addQuote(@RequestBody Quote quote){
        quoteRepository.save(quote);
        return quote;
    }

    @PutMapping("/{id}")
    public Quote edit(@PathVariable String id, @RequestBody Quote changedQuote){
        Quote quote = quoteRepository.findQuoteById(id);
        quote.setQuote(changedQuote.getQuote());
        quote.setISBN(changedQuote.getISBN());
        quoteRepository.save(quote);
        return quote;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        quoteRepository.deleteById(id);
    }
}
