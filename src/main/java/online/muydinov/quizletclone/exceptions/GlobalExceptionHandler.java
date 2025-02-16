package online.muydinov.quizletclone.exceptions;

import online.muydinov.quizletclone.exceptions.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Server error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Server Error: " + ex.getMessage());
    }

    @ExceptionHandler(CardSetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCardSetNotFound(CardSetNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(CardSetAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCardSetAlreadyExists(CardSetAlreadyExistsException ex) {
        return ResponseEntity.status(CONFLICT).body(new ErrorResponse(ex.getMessage(), CONFLICT.value()));
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCardNotFound(CardNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(ex.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedAccessException ex) {
        return ResponseEntity.status(FORBIDDEN).body(ex.getMessage());
    }

}
