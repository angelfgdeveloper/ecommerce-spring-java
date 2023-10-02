package com.angelfg.ecommerce.service.exception;

public class ErrorResponse {
    private String path;
    private String title;
    private String message;
    private int statusCode;

    public ErrorResponse(CustomException customException) {
        this.path = customException.getPath();
        this.title = customException.getHttpStatus().name();
        this.message = customException.getMessage();
        this.statusCode = customException.getHttpStatus().value();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}