package at.dkepr.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.User;



@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        //Hash the password
        newUser.setPassword(this.hash(newUser.getPassword()));
        try{
            return ResponseEntity.
            status(HttpStatus.CREATED)
            .body(this.repository.save(newUser));
        }catch(DataAccessException e) {
            String Message = "Error";
            if (e.getCause() instanceof ConstraintViolationException) {
                Message = "Email-Duplicate";
            }
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message);
        }

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody String[] payload) {
        Optional<User> optional = repository.findByEmail(payload[0]);

        if (optional.isPresent()) {
            User user = optional.get();
            String hashedPW = this.hash(payload[1]);

            if (user.getPassword().equals(hashedPW)){
                return ResponseEntity
                .status(HttpStatus.OK)
                .body("User eingeloggt!");
            }else {
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Das Passwort war nicht richtig!");
            }   
        } else {
            throw new UserNotFoundException(payload[0]);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/{email}")
    public User getUser(@PathVariable String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }


    private String hash(String s) {
        String myHash = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myHash;

    }

}
