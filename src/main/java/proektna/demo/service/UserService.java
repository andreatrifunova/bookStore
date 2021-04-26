package proektna.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import proektna.demo.model.enumerations.Role;
import proektna.demo.model.User;

public interface UserService extends UserDetailsService {

    User register(String username, String password,
                  String repeatPassword, String name, String surname, Role role);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User findByUsername(String username);

    void calculateCoupons(Integer useCoupons,User user);


}
