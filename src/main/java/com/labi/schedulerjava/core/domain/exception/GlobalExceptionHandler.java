package com.labi.schedulerjava.core.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error("Internal server error {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorMessage> handleBusinessRuleException(BusinessRuleException e) {
        logger.error("Business rule exception {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                e.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserAccountNotApprovedException.class)
    public ResponseEntity<ErrorMessage> handleUserAccountNotApprovedException(UserAccountNotApprovedException e) {
        logger.error("User account not approved exception {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("Bad credentials exception {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                "E-mail ou senha inválidos",
                HttpStatus.UNAUTHORIZED.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("Method argument not valid exception {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                e.getBindingResult().getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignInVolunteerException.class)
    public ResponseEntity<ErrorMessage> handleSignInVolunteerException(SignInVolunteerException e) {
        logger.error("Sign in volunteer exception {}", e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
