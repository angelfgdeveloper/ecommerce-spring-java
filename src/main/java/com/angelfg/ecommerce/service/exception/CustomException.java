package com.angelfg.ecommerce.service.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String path;
    public CustomException(
            String message,
            HttpStatus httpStatus,
            String path
    ) {
        super(message);
        this.httpStatus = httpStatus;
        this.path = path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}