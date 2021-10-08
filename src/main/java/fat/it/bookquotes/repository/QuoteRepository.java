package fat.it.bookquotes.repository;

import fat.it.bookquotes.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {
    List<Quote> findQuoteByISBN(String ISBN);
    Quote findQuoteById(String id);
}
