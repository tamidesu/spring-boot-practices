package kz.com.my_retro.exception;

public class RetroBoardNotFoundException extends RuntimeException {

    public RetroBoardNotFoundException() {
        super("RetroBoard Not Found");
    }

    public RetroBoardNotFoundException(String message) {
        super("RetroBoard Not Found: " + message);
    }

    public RetroBoardNotFoundException(String message, Throwable cause) {
        super("RetroBoard Not Found: " + message, cause);
    }
}
