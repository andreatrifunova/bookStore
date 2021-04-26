package proektna.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import proektna.demo.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<UserDetails> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username,String password);
}
