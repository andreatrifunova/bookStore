package proektna.demo.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proektna.demo.model.Author;
import proektna.demo.model.Book;
import proektna.demo.service.AuthorService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class AuthorController {

    private  final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthorsPage(Model model){
        List<Author> authors=this.authorService.findAll();
        model.addAttribute("authors",authors);
        return "authors";
    }

    @GetMapping("/authors/add-author")
    public String AddAuthorPage(Model model){
        return "add-author";
    }

    @PostMapping("authors/add-author/upload")
    public String uploadAuthor(@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd") Date date,
                               @RequestParam String country){

        Optional<Author> author=null;
        if (id != null) {
           author=this.authorService.edit(id,name,surname,date,country);
        } else {
            author=this.authorService.save(name,surname,date,country);
        }
        return "redirect:/authors";
    }

    @DeleteMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        this.authorService.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthorPage(@PathVariable Long id, Model model) {
        if (this.authorService.findById(id).isPresent()) {
            Author author = this.authorService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("author",author);
            return "add-author";
        }
        return "redirect:/authors?error=AuthorNotFound";
    }
}
