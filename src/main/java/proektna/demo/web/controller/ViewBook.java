package proektna.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import proektna.demo.model.Book;
import proektna.demo.service.BookService;

@Controller
@RequestMapping
public class ViewBook {

    private final BookService bookService;

    public ViewBook(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/view/{id}")
    public String getViewPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            model.addAttribute("book", book);
            return "view-book";
        }
        return "redirect:/books?error=BookNotFound";
    }
}
