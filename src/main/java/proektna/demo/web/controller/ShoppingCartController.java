package proektna.demo.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proektna.demo.model.ShoppingCart;
import proektna.demo.model.User;
import proektna.demo.service.ShoppingCartService;
import proektna.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public String getShoppingCartPage(HttpServletRequest req,
                                      Model model) {
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("books", this.shoppingCartService.listAllBooksInShoppingCart(shoppingCart.getId()));
//        model.addAttribute("bodyContent", "shopping-cart");
        return "shopping-cart";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBookFromShoppingCart(@PathVariable Long id,
                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        this.shoppingCartService.deleteBookFromShoppingCart(user.getUsername(),id);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/add-book/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addBookToShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/order")
    public String orderBooks(@RequestParam Integer useCoupons,
                             HttpServletRequest req,
                             Authentication authentication) {
        try {
        User user = (User) authentication.getPrincipal();
            this.userService.calculateCoupons(useCoupons, user);
            return "order-confirm";
        }
        catch (RuntimeException exception){
            return "redirect:/order-confirm?error=" + exception.getMessage();
        }
    }
}