package at.dkepr.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.dkepr.model.User;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    
    User findByEmail(@Param("email") String email);

    @Query("MATCH (u:User) RETURN n LIMIT 50")
    Collection<User> getAllUsers();
}
