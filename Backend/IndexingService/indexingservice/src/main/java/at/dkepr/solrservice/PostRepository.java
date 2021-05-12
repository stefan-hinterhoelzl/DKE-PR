package at.dkepr.solrservice;

import org.springframework.data.solr.repository.SolrCrudRepository;

import at.dkepr.entity.Post;

public interface PostRepository extends SolrCrudRepository<Post, String> {
    
}
