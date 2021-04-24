package at.dkepr.userservice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import at.dkepr.entity.User;

interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(Long id);
}
