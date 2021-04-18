package at.dkepr.user;

public class WrongPasswordException extends RuntimeException {

    WrongPasswordException() {
        super("Passwort inkorrekt");
    }
    
}
