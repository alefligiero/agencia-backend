package com.alefligiero.agenciabackend.controllers.exceptions;

import com.alefligiero.agenciabackend.services.exceptions.DatabaseException;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .statusCode(status.value())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleDatabaseException(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .statusCode(status.value())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        List<FieldMessage> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status);
        err.setStatusCode(status.value());
        err.setMessage("Validation error");
        err.setPath(request.getRequestURI());
        err.setErrors(errors);

        return ResponseEntity.status(status).body(err);
    }
}
