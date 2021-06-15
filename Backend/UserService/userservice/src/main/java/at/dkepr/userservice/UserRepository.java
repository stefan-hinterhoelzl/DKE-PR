package at.dkepr.userservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import at.dkepr.entity.User;

interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(Long id);

    List<User> findByIdIn(List<Long> userIdList);
}
