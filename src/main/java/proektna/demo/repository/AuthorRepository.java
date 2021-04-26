package proektna.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proektna.demo.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    //sega zasega e prazno zosto Jpa gi ima metodite osnovni
    //kaj repo nemame implementacii zosto imame bazi
}
