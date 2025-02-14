package online.muydinov.quizletclone.exceptions;

public class CardSetNotFoundException extends RuntimeException {
    public CardSetNotFoundException(String message) {
        super(message);
    }
}
