package at.dkepr.resource;

import at.dkepr.model.User;
import at.dkepr.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @PostMapping("/users/follows/{id1}/{id2}")
    public ResponseEntity<?> addRelationship(@PathVariable long id1, @PathVariable long id2) {
        repo.addRelationship(id1, id2);
        return ResponseEntity.status(HttpStatus.OK).body("Relationship added");
    }

    @DeleteMapping("/users/follows/{id1}/{id2}")
    public ResponseEntity<?> deleteRelationship(@PathVariable long id1, @PathVariable long id2) {
        repo.deleteRelationship(id1, id2);
        return ResponseEntity.status(HttpStatus.OK).body("Relationship delete");
    }

    @GetMapping("users/follows/{id}")
    public ResponseEntity<?> getFollows(@PathVariable long id) {
        Iterable<User> users = repo.getFollows(id);

        List<User> list = new ArrayList<User>();
        users.iterator().forEachRemaining(list::add);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("users/followedby/{id}")
    public ResponseEntity<?> getFollowedBy(@PathVariable long id) {
        Iterable<User> users = repo.getFollowedBy(id);

        List<User> list = new ArrayList<User>();
        users.iterator().forEachRemaining(list::add);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
