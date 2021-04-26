package proektna.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import proektna.demo.service.EmailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendMail(String subject, String message1) throws MessagingException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("andrea.trifunova16@gmail.com");
        mail.setSubject(subject);
        mail.setText(message1);


        javaMailSender.send(mail);
    }

}
