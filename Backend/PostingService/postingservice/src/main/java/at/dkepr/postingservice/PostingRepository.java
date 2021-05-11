package at.dkepr.postingservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import at.dkepr.entity.Post;

@Repository
public interface PostingRepository extends CrudRepository<Post, String> {

    
    
}
