package com.cursojava.curso.exception;

public class ErrorResponse {

    private Integer codeError;
    private String timestamp;
    private String message;

    public Integer getCodeError() {
        return codeError;
    }

    public void setCodeError(Integer codeError) {
        this.codeError = codeError;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
