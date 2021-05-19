package at.dkepr.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import at.dkepr.model.User;


public interface UserRepository extends Neo4jRepository<User, String> {
    
    @Query("MATCH (u:User) RETURN n LIMIT 50")
    Collection<User> getAllUsers();
}
