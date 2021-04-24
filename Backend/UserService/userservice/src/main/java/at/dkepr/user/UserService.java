package at.dkepr.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import at.dkepr.entity.User;

@Service
public class UserService {
    

    private UserRepository repo;
    private JwtTokenService jwtTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, JwtTokenService jwtTokenService) {
        this.repo = repo;
        this.jwtTokenService = jwtTokenService;
    }

    public JwtTokenResponse  generateJWTToken(String email, String password) {
        Optional<User> optional = repo.findByEmail(email);
        if (optional.isPresent()) {
            User user = optional.get();
            //Match the password
            if (passwordEncoder.matches(password, user.getPassword())){

                //Create the JWT TOKEN
                return new JwtTokenResponse(this.jwtTokenService.generateToken(user));
               
            }else {
                throw new WrongPasswordException();
            }   
        } else {
            throw new UserNotFoundException(email);
        }
                
    }
}
