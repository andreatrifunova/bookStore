package proektna.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proektna.demo.model.User;
import proektna.demo.model.exceptions.InvalidUserCredentialsException;
import proektna.demo.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }


    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    @GetMapping
    public String getLoginPage(Model model) {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request,
                        Model model) {
        User user = null;
        try{
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        }
        catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}

