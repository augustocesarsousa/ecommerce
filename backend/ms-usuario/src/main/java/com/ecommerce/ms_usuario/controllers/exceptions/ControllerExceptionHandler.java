package com.ecommerce.ms_usuario.controllers.exceptions;

import com.ecommerce.ms_usuario.services.exceptions.ExistingAttributeException;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(httpStatus.value());
        standardError.setError("Resouce not found");
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(ExistingAttributeException.class)
    public ResponseEntity<StandardError> existingAttribute(ExistingAttributeException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(httpStatus.value());
        standardError.setError("Existing attribute");
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }
}
