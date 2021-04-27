package at.dkepr.solrservice;

import org.springframework.data.solr.repository.SolrCrudRepository;

import at.dkepr.entity.UserSearchEntity;

public interface UserRepository extends SolrCrudRepository<UserSearchEntity, Long> {

}
