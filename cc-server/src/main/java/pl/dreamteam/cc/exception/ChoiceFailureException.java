package pl.dreamteam.cc.exception;

/**
 * Created by abu on 09.06.2016.
 */
public class ChoiceFailureException extends Exception{
    public ChoiceFailureException() {
        super();
    }

    public ChoiceFailureException(String message) {
        super(message);
    }

    public ChoiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChoiceFailureException(Throwable cause) {
        super(cause);
    }
}
