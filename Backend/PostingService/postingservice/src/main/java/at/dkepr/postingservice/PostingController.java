package at.dkepr.postingservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Post;

@RestController
public class PostingController {
    
    private final PostingRepository repository;

    PostingController(PostingRepository repo) {
        this.repository = repo;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/posts")
    public ResponseEntity<?> newUser(@RequestBody Post newPost) {
        return ResponseEntity.
        status(HttpStatus.CREATED)
        .body(this.repository.save(newPost));
    }

}
