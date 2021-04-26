package proektna.demo.service;

import proektna.demo.model.User;

public interface AuthService {

    User login(String username, String password);

}
