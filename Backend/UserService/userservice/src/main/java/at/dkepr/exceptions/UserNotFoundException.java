package at.dkepr.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User mit der Email "+email+" wurde nicht gefunden");
    }

    public UserNotFoundException(Long ID) {
        super("User mit der ID "+ID+" wurde nicht gefunden");
    }
    
}
