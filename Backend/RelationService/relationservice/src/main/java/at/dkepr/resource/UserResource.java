package at.dkepr.resource;

import at.dkepr.model.User;
import at.dkepr.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
    
    @Autowired
    UserRepository repo;

    @PostMapping("/user")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        return ResponseEntity.status(HttpStatus.OK).body(this.repo.save(newUser));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(repo.findById(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted user with ID: "+id);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUsers() {
        repo.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all user");
    }
}
