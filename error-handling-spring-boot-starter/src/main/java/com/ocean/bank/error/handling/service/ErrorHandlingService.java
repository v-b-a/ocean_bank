package com.ocean.bank.error.handling.service;

import com.ocean.bank.error.handling.dto.ExceptionDto;
import com.ocean.bank.error.handling.excepiton.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ErrorHandlingService {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingService.class);

    @ExceptionHandler({MissingRequestValueException.class, IllegalArgumentException.class})
    private ResponseEntity<ExceptionDto> handleMissingRequestHeaderException(Exception exception) {
        log.warn(exception.getStackTrace().toString());
        return ResponseEntity.badRequest().body(
                new ExceptionDto(getCurrentDateTime(), exception.getMessage(), null, 400)
        );
    }

    @ExceptionHandler({MissingKotlinParameterException.class, MethodArgumentNotValidException.class})
    private ResponseEntity<ExceptionDto> handleMissingKotlinParameter(Exception exception) {
        log.warn(exception.getStackTrace().toString());
        return ResponseEntity.badRequest().body(
                new ExceptionDto(getCurrentDateTime(), "Request does not match contract", null, 400)
        );
    }

    @ExceptionHandler(SQLException.class)
    private ResponseEntity<ExceptionDto> handlePSQLException(SQLException exception) {
        log.warn(exception.getStackTrace().toString());
        return ResponseEntity.status(503).body(
                new ExceptionDto(getCurrentDateTime(), exception.getMessage(), null, 503)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ExceptionDto> handleNotFoundException(RuntimeException exception) {
        log.warn(exception.getStackTrace().toString());
        return ResponseEntity.status(404).body(
                new ExceptionDto(getCurrentDateTime(), exception.getMessage(), null, 404)
        );
    }

    private String getCurrentDateTime() {
        return java.time.LocalDateTime.now().toString();
    }


}
