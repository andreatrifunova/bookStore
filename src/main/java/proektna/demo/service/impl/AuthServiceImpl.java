package proektna.demo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proektna.demo.model.User;
import proektna.demo.model.exceptions.InvalidArgumentsException;
import proektna.demo.model.exceptions.InvalidUserCredentialsException;
import proektna.demo.repository.UserRepository;
import proektna.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
