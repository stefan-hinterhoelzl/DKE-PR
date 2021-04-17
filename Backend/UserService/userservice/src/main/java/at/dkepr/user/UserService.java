package at.dkepr.user;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import at.dkepr.entity.User;
import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class UserService {
    
   EmailService emailsender;
    void sendConfirmationMail(String userMail, String token) {

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Bestätigungslink!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Danke für Ihre Registrierung! Bestätigen Sie diese mit diesem Link!." + "http://localhost:8080/sign-up/confirm?token="
                        + token);
    
        emailsender.sendEmail(mailMessage);
    }
    
}
