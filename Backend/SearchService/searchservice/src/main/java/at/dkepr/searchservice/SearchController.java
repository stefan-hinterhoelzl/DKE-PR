package at.dkepr.searchservice;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Post;
import at.dkepr.entity.UserSearchEntity;

@RestController
public class SearchController {
    
    
    private final UserRepository repository;
    private final PostRepository prepository;


    public SearchController(UserRepository repository, PostRepository prepository) {
        this.repository = repository;
        this.prepository = prepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("users/{searchterm}")
    public ResponseEntity<List<UserSearchEntity>> getUsers(@PathVariable String searchterm) {
        List<UserSearchEntity> users =  this.repository.findByCustomQuery(searchterm);
        return ResponseEntity.status(HttpStatus.OK)
            .body(users);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("posts/{searchterm}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable String searchterm) {
        List<Post> posts =  this.prepository.findByCustomQuery(searchterm);
        return ResponseEntity.status(HttpStatus.OK)
            .body(posts);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("users") 
    public ResponseEntity<List<UserSearchEntity>> getAllUsers() {
        
        List<UserSearchEntity> users =  this.repository.findByCustomQuery("*");
        return ResponseEntity.status(HttpStatus.OK)
            .body(users);
        
    }


}
