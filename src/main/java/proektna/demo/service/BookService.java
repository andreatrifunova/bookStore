package proektna.demo.service;

import proektna.demo.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByName(String name);

    Optional<Book> save(String name, Long author,String izdavac,String description,
            String zanr,Double price,Integer coupons,String picturePath);


    Optional<Book> edit(Long id, String name, Long authorId,String izdavac, String description,String zanr,
                        Double price,Integer coupons, String picturePath);

    void deleteById(Long id);

    List<Book> findBookByZanr(String zanr);

    List <Book> firstFiveBooks();

    void votes(Long [] array);

}
