package proektna.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proektna.demo.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByName(String name);

    void deleteByName(String name);

    List<Book> findTop5ByOrderByVotesDesc();


}
