package at.dkepr.user;


import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserController(UserRepository repository) {
        this.repository = repository;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        //Hash the password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        

        try{
            return ResponseEntity.
            status(HttpStatus.CREATED)
            .body(this.repository.save(newUser));
        }catch(DataAccessException e) {
            String Message = "Error";
            if (e.getCause() instanceof ConstraintViolationException) {
                Message = "Diese Mail Adresse existiert bereits.";
            }

            e.printStackTrace();
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
            //Match the password
            if (passwordEncoder.matches(payload.getPassword(), user.getPassword())){

                //Create the JWT TOKEN
                String token = "";
                try{
                    //Add some Claims
                    Algorithm algo = Algorithm.HMAC256("NichtGanzSoGeheimesSecret");
                    token = JWT.create().withIssuer("DefinitelyNotTwitter").withClaim("Email", user.getEmail()).withClaim("Firstname", user.getFirstname()).withClaim("Lastname", user.getLastname())
                    .withClaim("creationTime", System.currentTimeMillis()).withClaim("expirationTime", System.currentTimeMillis()+3600000).sign(algo);
                } catch (JWTCreationException e) {
                    e.printStackTrace();
                }
               String Message = "{\"response\": \"success\", \"token\":\""+token+"\"}";
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
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {
       User userToChange = this.getUser(id);

       //only change the changable fields
       userToChange.setFirstname(user.getFirstname());
       userToChange.setLastname(user.getLastname());
       userToChange.setPhonenumber(user.getPhonenumber());
       userToChange.setPokemonid(user.getPokemonid());

       return ResponseEntity
       .status(HttpStatus.OK)
       .body(repository.save(userToChange));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User userToDelete = this.getUser(id);

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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("userPerID/{id}")
    public User getUser(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


    

}
