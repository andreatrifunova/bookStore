package proektna.demo.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proektna.demo.model.Author;
import proektna.demo.model.Book;
import proektna.demo.model.exceptions.AuthorNotFoundException;
import proektna.demo.model.exceptions.BookNotFoundException;
import proektna.demo.repository.AuthorRepository;
import proektna.demo.repository.BookRepository;
import proektna.demo.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Book> save(String name, Long authorId, String izdavac,
                               String description,String zanr,
                               Double price,Integer coupons, String picturePath) {

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        return Optional.of(this.bookRepository.save(new Book(name, author,izdavac,
                description,zanr,price,coupons,picturePath)));
    }


    @Override
    @Transactional
    public Optional<Book> edit(Long id, String name, Long authorId,String izdavac, String description,String zanr,
                               Double price,Integer coupons, String picturePath) {

        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(name);
        book.setIzdavackaKukja(izdavac);
        book.setPrice(price);
        book.setDescription(description);
        book.setZanr(zanr);
        book.setCouponsForBook(coupons);
        book.setPicturePath(picturePath);

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
        book.setAuthor(author);

        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findBookByZanr(String zanr) {
        return this.bookRepository.findAll().stream()
                .filter(r->r.getZanr().equals(zanr)).collect(Collectors.toList());
    }


    @Override
    public List<Book> firstFiveBooks() {
        return this.bookRepository.findTop5ByOrderByVotesDesc();
    }

    @Override
    public void votes(Long [] array) {

        Book book=null;

        for(Long item : array){
            book=this.bookRepository.findById(item).get();
            Integer n=book.getVotes();
            n=n+1;
            book.setVotes(n);
            this.bookRepository.save(book);
        }

    }

}
