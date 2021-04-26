package proektna.demo.service.impl;

import org.springframework.stereotype.Service;
import proektna.demo.model.Author;
import proektna.demo.model.Book;
import proektna.demo.model.exceptions.AuthorNotFoundException;
import proektna.demo.model.exceptions.BookNotFoundException;
import proektna.demo.repository.AuthorRepository;
import proektna.demo.service.AuthorService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> save(String name, String surname, Date birthDate,String country) {
        return Optional.of(this.authorRepository.save(new Author(name, surname,birthDate,country)));
    }

    @Override
    public Optional<Author> edit(Long id, String name, String surname, Date date, String country) {

        Author author=this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        author.setName(name);
        author.setSurname(surname);
        author.setBirthDate(date);
        author.setCountry(country);

        return Optional.of(this.authorRepository.save(author));
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }

}
