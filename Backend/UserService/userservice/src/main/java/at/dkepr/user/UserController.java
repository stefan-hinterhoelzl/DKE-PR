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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Credential;
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
                Message = "Diese Mail Adresse existiert bereits.";
            }
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Message);
        }

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Credential payload) {
        Optional<User> optional = repository.findByEmail(payload.getEmail());

        if (optional.isPresent()) {
            User user = optional.get();
            String hashedPW = this.hash(payload.getPassword());

            if (user.getPassword().equals(hashedPW)){
               String Message = "{\"response\": \"Eingeloggt!\"}";
               return ResponseEntity.
               status(HttpStatus.OK).body(Message);
               
            }else {
                throw new WrongPasswordException();
            }   
        } else {
            throw new UserNotFoundException(payload.getEmail());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/user/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String email) {
       User userToChange = this.getUser(email);
       user.setPassword(this.hash(user.getPassword()));

       userToChange.setFirstname(user.getFirstname());
       userToChange.setLastname(user.getLastname());
       userToChange.setPhonenumber(user.getPhonenumber());
       userToChange.setPassword(user.getPassword());

       return ResponseEntity
       .status(HttpStatus.OK)
       .body(repository.save(userToChange));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        User userToDelete = this.getUser(email);

        repository.deleteById(userToDelete.getId());

        return ResponseEntity.
                status(HttpStatus.OK)
                .body("\"response\":\"Deleted\"");
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
