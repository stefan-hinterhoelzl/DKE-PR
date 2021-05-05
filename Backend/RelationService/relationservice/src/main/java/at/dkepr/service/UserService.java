package at.dkepr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import at.dkepr.model.User;
import at.dkepr.repository.UserRepository;

public class UserService {

    @Autowired
    UserRepository userRepository;

    public Collection<User> getAll() {
        return userRepository.getAllUsers();

    }

}
