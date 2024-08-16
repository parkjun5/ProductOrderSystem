package kr.co.system.homework.support.ui;

import jakarta.persistence.EntityNotFoundException;
import kr.co.system.homework.support.exception.SoldOutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackages = "kr.co._39cm.homework")
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalStateException.class, IllegalFormatException.class,
            IllegalArgumentException.class, NoSuchElementException.class, TypeMismatchException.class})
    public ResponseEntity<Object> handleBadRequest(Exception e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleCustomException(BadRequestException e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleCustomException(EntityNotFoundException e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
        return getErrorResponseBy(e.getMessage(), httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(ClientAbortException e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(AuthenticationException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return getErrorResponseBy(e.getMessage(), httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(SoldOutException e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(RuntimeException e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(Exception e) {
        return getErrorResponseBy(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getErrorResponseBy(String message,
                                                      HttpStatus httpStatus) {
        log.error(message);

        return ResponseEntity.status(httpStatus)
                .body(new ErrorDataResponse(httpStatus.getReasonPhrase(), message));
    }

    public record ErrorDataResponse(
            String status,
            String errorMessage
    ) {
    }
}
