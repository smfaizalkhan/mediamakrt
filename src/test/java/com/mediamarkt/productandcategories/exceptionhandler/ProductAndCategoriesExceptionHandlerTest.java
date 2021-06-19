package com.mediamarkt.productandcategories.exceptionhandler;

import com.mediamarkt.productandcategories.error.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class ProductAndCategoriesExceptionHandlerTest {

    @InjectMocks
    private ProductAndCategoriesExceptionHandler productAndCategoriesExceptionHandler;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleInputMismatchException() {
    }

    @Test
    void handleResponseStatusException() {
        ResponseStatusException responseStatusException =
                new ResponseStatusException(HttpStatus.NOT_FOUND);
        ResponseEntity<Object> responseEntity = productAndCategoriesExceptionHandler.handleResponseStatusException(responseStatusException);
        assertTrue(responseEntity.getBody() instanceof ApiError);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals(responseStatusException.getMessage(),apiError.getDescription());
    }
}