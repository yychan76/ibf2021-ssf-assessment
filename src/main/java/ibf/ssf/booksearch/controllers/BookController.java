package ibf.ssf.booksearch.controllers;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf.ssf.booksearch.models.Book;
import ibf.ssf.booksearch.services.BookService;

@Controller
@RequestMapping(
    path = "/book",
    produces = {MediaType.TEXT_HTML_VALUE}
)
public class BookController {
    private final Logger log = Logger.getLogger(BookController.class.getName());

    @Autowired
    private BookService bookService;

    @GetMapping("{works_id}")
    public String getBook(@PathVariable String works_id, Model model) {
        Optional<Book> bookOpt = bookService.getBook(works_id);
        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
        }
        return "book";
    }
}
