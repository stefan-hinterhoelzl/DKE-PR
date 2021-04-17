package at.dkepr.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import at.dkepr.entity.User;

interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
}
