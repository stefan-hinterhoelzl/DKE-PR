package at.dkepr.user;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    EmailService emailsender;

    void sendConfirmationMail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Willkommen!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Willkommen auf unserer Social Media Plattform! Ihr Account ist bereits einsatzbereit!");

        emailsender.sendEmail(mailMessage);
    }

}
