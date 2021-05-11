package at.dkepr.searchservice;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import at.dkepr.entity.UserSearchEntity;

public interface UserRepository extends SolrCrudRepository<UserSearchEntity, String> {


    @Query("*?0*")
    public List<UserSearchEntity> findByCustomQuery(String searchTerm);
    
}
