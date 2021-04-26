package proektna.demo.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMail(String subject, String message) throws MessagingException;

}
