package at.dkepr.userservice;


import java.sql.SQLException;
import java.util.Optional;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import at.dkepr.entity.Credential;
import at.dkepr.entity.PasswordChangeCredential;
import at.dkepr.entity.StringResponse;
import at.dkepr.entity.User;
import at.dkepr.entity.UserSearchEntity;
import at.dkepr.exceptions.UserNotFoundException;
import at.dkepr.exceptions.WrongPasswordException;
import at.dkepr.security.JwtTokenResponse;
import at.dkepr.security.JwtTokenService;



@RestController
public class UserController {

    private final UserRepository repository;
    private final JwtTokenService jwtservice;
   
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JmsTemplate jmsTemplate;

    RestTemplate restTemplate = new RestTemplate();


    UserController(UserRepository repository, JwtTokenService jwt) {
        this.repository = repository;
        this.jwtservice = jwt;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        //Hash the password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        
        try{
            
            User addedUser = this.repository.save(newUser);

            //send to solr
            Queue queue = new ActiveMQQueue("user-add-queue");
            jmsTemplate.convertAndSend(queue, new UserSearchEntity(String.valueOf(addedUser.getId()), addedUser.getEmail(), addedUser.getFirstname(), addedUser.getLastname(), addedUser.getPokemonid()));

            //send to neo4j
            

            return ResponseEntity.
            status(HttpStatus.CREATED)
            .body(addedUser);
        }catch(DataAccessException e) {
            String Message = "Error";
            if (e.getCause() instanceof ConstraintViolationException) {
                Message = "Diese Mail Adresse existiert bereits.";
            }

            if (e.getCause() instanceof SQLException) {
                Message = "Datenbankfehler - Wurde der Table und die Sequence erstellt?";
            }
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new StringResponse(Message));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> authenticate(@RequestBody Credential payload) {
        Optional<User> optional = repository.findByEmail(payload.getEmail());

        if (optional.isPresent()) {
            User user = optional.get();
            //Match the password
            if (passwordEncoder.matches(payload.getPassword(), user.getPassword())){

                //Create the JWT TOKEN
                JwtTokenResponse res = new JwtTokenResponse(this.jwtservice.generateToken(user));
                return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
               
            }else {
                throw new WrongPasswordException();
            }   
        } else {
            System.out.println("I am here");
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


       //send to solr
       Queue queue = new ActiveMQQueue("user-add-queue");

       UserSearchEntity newsearchuser = new UserSearchEntity(String.valueOf(userToChange.getId()), userToChange.getEmail(), userToChange.getFirstname(), userToChange.getLastname(), userToChange.getPokemonid());

       this.jmsTemplate.convertAndSend(queue, newsearchuser);

       return ResponseEntity
       .status(HttpStatus.OK)
       .body(repository.save(userToChange));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/password/{id}")
    public ResponseEntity<?> updateUserPassword(@RequestBody PasswordChangeCredential payload, @RequestHeader("authorization") String token, @PathVariable Long id) {
        String callerTokenMail = jwtservice.getEmailFromToken(token.substring(7));
        User userToChange = this.getUser(id);

        //checks
        if (!callerTokenMail.equals(userToChange.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Validierung des Users fehlgeschlagen - Abmeldung");
        }

        //Check the old pw
        if (passwordEncoder.matches(payload.getOldpassword(), userToChange.getPassword())) {
                if (payload.getNewpassword().equals(payload.getNewpasswordconfirm())) {
                    userToChange.setPassword(passwordEncoder.encode(payload.getNewpassword()));
                } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwörter stimmen nicht überein");
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(userToChange));

        } else throw new WrongPasswordException();

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("user/{id}")
    public ResponseEntity<StringResponse> deleteUser(@PathVariable Long id) {
        User userToDelete = this.getUser(id);

        repository.deleteById(userToDelete.getId());

        //send to solr
        Queue queue = new ActiveMQQueue("user-delete-queue");
        UserSearchEntity deleteuser = new UserSearchEntity(String.valueOf(userToDelete.getId()), userToDelete.getEmail(), userToDelete.getFirstname(), userToDelete.getLastname(), userToDelete.getPokemonid());


        this.jmsTemplate.convertAndSend(queue, deleteuser);
        

        return ResponseEntity.
                status(HttpStatus.OK)
                .body(new StringResponse("Deleted"));
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
