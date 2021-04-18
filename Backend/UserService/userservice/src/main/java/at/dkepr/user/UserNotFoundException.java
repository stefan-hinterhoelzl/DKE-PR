package at.dkepr.user;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String email) {
        super("User mit der Email "+email+" wurde nicht gefunden");
    }
    
}
