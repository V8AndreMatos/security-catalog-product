package com.security.catalog.product.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e , HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Resource Not Found");
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI()); //Pega o caminho da requisição /categories
        return ResponseEntity.status(status).body(standardError);


    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBase(DataBaseException e , HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("DataBase Exception");
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI()); //Pega o caminho da requisição /categories
        return ResponseEntity.status(status).body(standardError);

    }
}
