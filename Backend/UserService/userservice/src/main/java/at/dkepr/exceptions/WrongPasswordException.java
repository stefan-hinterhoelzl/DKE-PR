package at.dkepr.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Passwort inkorrekt");
    }
    
}
