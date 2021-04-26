package proektna.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proektna.demo.model.Book;
import proektna.demo.service.BookService;
import proektna.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

    private final BookService bookService;

    public HomeController( BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<Book> orderBooks=this.bookService.firstFiveBooks();
        model.addAttribute("orderBooks",orderBooks);
        return "home";
    }

}

