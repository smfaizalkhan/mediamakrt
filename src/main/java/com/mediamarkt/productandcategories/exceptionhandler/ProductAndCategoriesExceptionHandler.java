package com.mediamarkt.productandcategories.exceptionhandler;

import com.mediamarkt.productandcategories.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;

@RestControllerAdvice
public class ProductAndCategoriesExceptionHandler {

    @ExceptionHandler(value = InputMismatchException.class)
    public ResponseEntity<Object> handleInputMismatchException(InputMismatchException ex){
        final ApiError apiError = new ApiError(String.valueOf(HttpStatus.BAD_REQUEST.value()),ex.getClass().getSimpleName(),ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex){
        final ApiError apiError = new ApiError(String.valueOf(HttpStatus.NOT_FOUND.value()),ex.getClass().getSimpleName(),ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
