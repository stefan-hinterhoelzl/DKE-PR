package at.dkepr.resource;

import at.dkepr.model.User;
import at.dkepr.repository.UserRepository;
import at.dkepr.service.UserService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository repo;

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }


    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(repo.save(newUser));
    }

}
