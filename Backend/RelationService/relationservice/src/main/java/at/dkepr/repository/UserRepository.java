package at.dkepr.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import at.dkepr.model.User;


public interface UserRepository extends Neo4jRepository<User, Long> {
    
    @Query("MATCH (u:User) RETURN n LIMIT 25")
    Collection<User> getAllUsers();


    @Query("MATCH (a1:User), (a2:User) " +
    "WHERE a1.id = $0 AND a2.id = $1" +
    "CREATE (a1)-[:follows]->(a2)")
    void addRelationship(long id1, long id2);

    @Query("MATCH (a1:User{id : $0})" +
    "-[r:follows]->(a2:User{id : $1}) DELETE r")
    void deleteRelationship(long id1, long id2);

}
