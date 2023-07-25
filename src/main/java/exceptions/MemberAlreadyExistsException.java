package exceptions;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException() {
        super();
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
