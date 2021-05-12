package at.dkepr.postingservice;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Post;
import at.dkepr.entity.StringResponse;

@RestController
public class PostingController {
    
    private final PostingService service;

    PostingController(PostingService service) {
        this.service = service;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("post/{id}")
    public ResponseEntity<?> getPostbyId(@PathVariable String id) {
        Optional<Post> post = this.service.findById(id);

        if (post.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(post.get());
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Not Found"));
    }

    // @GetMapping("post/latestpost/{author}")
    // public ResponseEntity<?> getLatestPostByAuthor(@PathVariable String author) {
    //     Optional<Post> post = this.repository.getLatestPostByAuthor(author);

    //     if (post.isPresent()) {
    //         return ResponseEntity.status(HttpStatus.OK).body(post.get());
    //     }
    //     else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Not Found"));
    // }


    //Extrem dreckige Lösung, aber hab des Max Attribut in da Query ned zum laufen bracht - geht im UI von Couchbase
    @GetMapping("post/latestpost/{author}")
    public ResponseEntity<?> getLatestPostByAuthor(@PathVariable String author) {
        List<Post> posts = this.service.findByAuthor(author);

        if(posts.size() >= 1) {
            Post newestPost = posts.get(0);
            for (int i = 1; i<posts.size(); i++) {
                if (posts.get(i).getCreatedAt() > newestPost.getCreatedAt()) {
                    newestPost = posts.get(i);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(newestPost);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Der User kat keine Posts abgegeben"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("posts/{author}")
    public ResponseEntity<?> getAllPostsbyAuthor(@PathVariable String author) {
        List<Post> posts = this.service.findByAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
