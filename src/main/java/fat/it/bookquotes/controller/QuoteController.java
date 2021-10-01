package fat.it.bookquotes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    @RequestMapping("/")
    public String GiveHelloWorld(){
        return "Hello world";
    }
}
