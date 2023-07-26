package exceptions;

public class NicknameAlreadyExistsException extends RuntimeException {
    public NicknameAlreadyExistsException() {
        super();
    }

    public NicknameAlreadyExistsException(String message) {
        super(message);
    }
}
