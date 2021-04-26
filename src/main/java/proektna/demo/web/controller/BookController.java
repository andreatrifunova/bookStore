package proektna.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proektna.demo.model.Author;
import proektna.demo.model.Book;
import proektna.demo.service.AuthorService;
import proektna.demo.service.BookService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger("BookController.class");
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @GetMapping("/books")
    public String getBookPage(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("book", book);
            return "add-book";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/add-book")
    public String getPage(Model model){
        List<Author> authors=this.authorService.findAll();
        model.addAttribute("authors",authors);
        return "add-book";
    }

    @PostMapping("add-book/upload")
    public String uploadImage(@RequestParam(required = false) Long id,
                              @RequestParam String bookName,
                              @RequestParam Long authorId,
                              @RequestParam String izdavac,
                              @RequestParam String description,
                              @RequestParam String zanr,
                              @RequestParam Double cena,
                              @RequestParam Integer coupons,
                              @RequestParam MultipartFile imageFile) throws IOException {


        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Optional<Book> book=null;
        if (id != null) {
            book=this.bookService.edit(id, bookName,authorId,izdavac,description,zanr,cena,coupons,fileName);
        } else {
            book=this.bookService.save(bookName,authorId,izdavac,description,zanr,cena,coupons,fileName);
        }

        String uploadDir = "book-photos/" + book.get().getId();

        FileUploadUtil.saveFile(uploadDir, fileName, imageFile);

        return "redirect:/books";

    }

    @GetMapping("/{zanr}")
    public String getZanrPage(@PathVariable String zanr,Model model){
        if(!this.bookService.findBookByZanr(zanr).isEmpty()) {
            List<Book> books = this.bookService.findBookByZanr(zanr);
            model.addAttribute("books", books);
            return "books";
        }
        return "redirect:/books?error=BookNotFound";
    }

}

