package at.dkepr.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@RestController
public class UserController {

    private UserRepository repository;

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        //Hash the password
        newUser.setPassword(this.hash(newUser.getPassword()));

        //Store the User, Create the Http Response
        try {
            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.repository.save(newUser));
        }catch (DataAccessException e) {
            return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body("Es ist ein Fehler aufgetreten. User wurde nicht angelegt.");
        }
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> newUser(@RequestBody String[] payload) {
        return null;
        
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
