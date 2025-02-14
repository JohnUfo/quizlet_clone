package online.muydinov.quizletclone.exceptions;

public class CardSetAlreadyExistsException extends RuntimeException {
    public CardSetAlreadyExistsException(String message) {
        super(message);
    }
}
