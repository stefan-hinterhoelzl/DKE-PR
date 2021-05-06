package at.dkepr.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import at.dkepr.model.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends Neo4jRepository<User, Long> {
    
    User findByEmail(@Param("email") String email);

    @Query("MATCH (u:User) RETURN n LIMIT 50")
    Collection<User> getAllUsers();
}
