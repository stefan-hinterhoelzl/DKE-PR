package at.dkepr.searchservice;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.UserSearchEntity;

@RestController
public class SearchController {
    
    
    private final UserRepository repository;


    public SearchController(UserRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("users/{searchterm}")
    public ResponseEntity<List<UserSearchEntity>> getUsers(@PathVariable String searchterm) {

        System.out.println(searchterm);

        List<UserSearchEntity> users =  this.repository.findByCustomQuery(searchterm);

        return ResponseEntity.status(HttpStatus.OK)
            .body(users);

    }


}
