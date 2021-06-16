package at.dkepr.postingservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import at.dkepr.entity.Post;

@Component
public class PostingService {

    private final PostingRepository repository;

    PostingService(PostingRepository repo) {
        this.repository = repo;
    }


    public Post savePost(Post post) {
        return this.repository.save(post);

    }

    public Optional<Post> findById(String id) {
        return this.repository.findById(id);
    }

    public List<Post> findByAuthor(Long authorid) {
        return this.repository.findByAuthorid(authorid);
    }

    public void deletebyId(String id) {
        this.repository.deleteById(id);
    }

    public void deleteAllbyAuthorId(Long id) {
       this.repository.deleteByAuthorid(id);
    }
    
    public Iterable<Post> getAll() {
        return this.repository.findAll();
    }

}
