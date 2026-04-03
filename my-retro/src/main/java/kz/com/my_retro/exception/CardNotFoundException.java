package kz.com.my_retro.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {
        super("Card Not Found");
    }

    public CardNotFoundException(String message) {
        super("Card Not Found: " + message);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super("Card Not Found: " + message, cause);
    }
}
