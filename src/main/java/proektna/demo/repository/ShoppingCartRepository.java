package proektna.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proektna.demo.model.Book;
import proektna.demo.model.ShoppingCart;
import proektna.demo.model.User;
import proektna.demo.model.enumerations.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository
        extends JpaRepository<ShoppingCart,Long> {

    Optional<ShoppingCart> findByUserAndStatus(User u, Status status);

    List<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> deleteByUserAndStatus(User u, Book b);

}
