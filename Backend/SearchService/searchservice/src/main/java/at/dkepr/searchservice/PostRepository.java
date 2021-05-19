package at.dkepr.searchservice;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import at.dkepr.entity.Post;

public interface PostRepository extends SolrCrudRepository<Post, String> {


    @Query("content:*?0* or createdAt:*?0*")
    public List<Post> findByCustomQuery(String searchTerm);
    
}
