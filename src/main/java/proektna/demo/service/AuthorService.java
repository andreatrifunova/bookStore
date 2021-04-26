package proektna.demo.service;

import proektna.demo.model.Author;
import proektna.demo.model.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(Long id);
    List<Author> findAll();
    Optional<Author> save(String name,String surname,Date date,String country);
    Optional<Author> edit(Long id, String name,String surname,Date date,String country);
    void deleteById(Long id);
}
