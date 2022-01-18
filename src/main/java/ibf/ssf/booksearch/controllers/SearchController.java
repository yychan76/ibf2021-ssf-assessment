package ibf.ssf.booksearch.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf.ssf.booksearch.models.Book;
import ibf.ssf.booksearch.services.BookService;

@Controller
@RequestMapping(
    path = "/search",
    produces = {MediaType.TEXT_HTML_VALUE}
)
public class SearchController {
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    BookService bookService;

    @GetMapping
    public String handleSearch(@RequestParam String book, Model model) {
        logger.info("User searched for: %s".formatted(book));
        List<Book> results = bookService.search(book);
        model.addAttribute("book", book);
        model.addAttribute("results", results);
        return "results";
    }
}
