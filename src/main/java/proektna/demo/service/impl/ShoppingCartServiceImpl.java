package proektna.demo.service.impl;

import org.springframework.stereotype.Service;
import proektna.demo.model.Book;
import proektna.demo.model.ShoppingCart;
import proektna.demo.model.User;
import proektna.demo.model.enumerations.Status;
import proektna.demo.model.exceptions.BookAlreadyInShoppingCartException;
import proektna.demo.model.exceptions.BookNotFoundException;
import proektna.demo.model.exceptions.ShoppingCartNotFoundException;
import proektna.demo.model.exceptions.UserNotFoundException;
import proektna.demo.repository.ShoppingCartRepository;
import proektna.demo.repository.UserRepository;
import proektna.demo.service.BookService;
import proektna.demo.service.ShoppingCartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   BookService bookService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Book> listAllBooksInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getBookList();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = (User) this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, Status.CREATED)
            .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addBookToShoppingCart(String username, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Book book = this.bookService.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        if(shoppingCart.getBookList()
                .stream().filter(i -> i.getId().equals(bookId))
                .collect(Collectors.toList()).size() > 0)
            throw new BookAlreadyInShoppingCartException(bookId, username);
        shoppingCart.getBookList().add(book);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteBookFromShoppingCart(String username,Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.getBookList().removeIf(
                r-> r.getId().equals(bookId));
        this.shoppingCartRepository.save(shoppingCart);
    }

}
