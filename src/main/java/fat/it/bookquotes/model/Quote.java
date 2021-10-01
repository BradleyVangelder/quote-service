package fat.it.bookquotes.model;

import org.springframework.data.annotation.Id;

public class Quote {
    private String quote;
    private String category;
    private String ISBN;

    public Quote() {}

    public Quote(String quote, String category, String ISBN) {
        setISBN(ISBN);
        setCategory(category);
        setQuote(quote);
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
