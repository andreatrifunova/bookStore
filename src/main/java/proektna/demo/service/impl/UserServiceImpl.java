package proektna.demo.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proektna.demo.model.ShoppingCart;
import proektna.demo.model.enumerations.Role;
import proektna.demo.model.User;
import proektna.demo.model.exceptions.*;
import proektna.demo.repository.UserRepository;
import proektna.demo.service.ShoppingCartService;
import proektna.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShoppingCartService cartService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ShoppingCartService cartService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public User findByUsername(String username) {
        return (User) this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void calculateCoupons(Integer useCoupons,User user) {
        Integer oldCoupons=user.getCoupons();
        ShoppingCart cart=this.cartService.getActiveShoppingCart(user.getUsername());

        if(useCoupons>oldCoupons){
            throw new UserEnterMoreCoupondThanHave();
        }
        oldCoupons=oldCoupons-useCoupons;
        Integer newCoupons=0;

        for(int i=0;i<cart.getBookList().size();i++){
            newCoupons+=cart.getBookList().get(i).getCouponsForBook();
        }

        oldCoupons=oldCoupons+newCoupons;
        user.setCoupons(oldCoupons);
        userRepository.save(user);
    }


    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username,passwordEncoder.encode(password),name,surname,userRole);
        return userRepository.save(user);
    }
}
