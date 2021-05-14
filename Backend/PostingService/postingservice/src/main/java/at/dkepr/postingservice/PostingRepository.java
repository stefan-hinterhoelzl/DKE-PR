package at.dkepr.postingservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import at.dkepr.entity.Post;

@Repository
public interface PostingRepository extends CrudRepository<Post, String> {


    //Haut net hin, später numal versuchen -> erstmal dreckige lösung
    // @Query("SELECT MAX([createdAt, postings])[1] As latestpost FROM postings where author = author")
    // public Optional<Post> getLatestPostByAuthor(String author);


    public List<Post> findByAuthorid(String authorid);

    public void deleteByAuthorid(String authorid);
    
}
