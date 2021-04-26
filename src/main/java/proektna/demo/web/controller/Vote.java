package proektna.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proektna.demo.model.Author;
import proektna.demo.model.Book;
import proektna.demo.service.AuthorService;
import proektna.demo.service.BookService;

import java.sql.Array;
import java.util.List;

@Controller
@RequestMapping(value = {"/vote"})
public class Vote {


    private final BookService bookService;

    public Vote(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getVotePage(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        return "vote";
    }

    @PostMapping("/upload")
    public String userVote(@RequestParam Long firstChoice,
                           @RequestParam Long secondChoice,
                           @RequestParam Long thirdChoice,
                           @RequestParam Long fourthChoice,
                           @RequestParam Long fifthChoice) {

        Long [] array= {firstChoice,secondChoice,thirdChoice,fourthChoice,fifthChoice};
        this.bookService.votes(array);

        return "redirect:/home";
    }

}