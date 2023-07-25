package exceptions;

public class MemberInvalidInputException extends RuntimeException {
    public MemberInvalidInputException() {
        super();
    }

    public MemberInvalidInputException(String message) {
        super(message);
    }
}
