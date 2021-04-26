package proektna.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proektna.demo.service.EmailService;

import javax.mail.MessagingException;

@Controller
@RequestMapping(value = {"/contact"})
public class Contact {

    public final EmailService emailService;

    public Contact(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("/sendMail")
    public String getSendMail(){
        return "sendMail";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String subject,
                              @RequestParam String comments) throws MessagingException {


        emailService.sendMail(subject,comments);
        return "redirect:/sendMail";
    }


}
