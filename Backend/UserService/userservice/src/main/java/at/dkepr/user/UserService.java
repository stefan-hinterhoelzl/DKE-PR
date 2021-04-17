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
public class UserService implements UserDetailsService  {
    
    UserRepository repository;
    BCryptPasswordEncoder encoder;
    ConfirmationTokenService tokenservice;
    EmailService emailsender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> optionalUser = repository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User mit der Email {0} wurde nicht gefunden.", email));
        }
    }

    void signUpUser(User user) {
        final String encryptedPassword = encoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        repository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);

        tokenservice.saveConfirmationToken(token);
        
    }

    void confirmUser(ConfirmationToken token) {
       User user = token.getUser();

       user.setEnabled(true);

       repository.save(user);

       tokenservice.deleteConfirmationToken(token.getId());

    }

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
