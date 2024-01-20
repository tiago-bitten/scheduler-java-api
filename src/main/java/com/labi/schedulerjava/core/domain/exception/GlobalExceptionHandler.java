package com.labi.schedulerjava.core.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
}
