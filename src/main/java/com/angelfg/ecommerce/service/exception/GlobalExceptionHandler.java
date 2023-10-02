package com.angelfg.ecommerce.service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        ErrorResponse errorResponse = new ErrorResponse(customException);
        return new ResponseEntity<>(errorResponse, customException.getHttpStatus());
    }

}
