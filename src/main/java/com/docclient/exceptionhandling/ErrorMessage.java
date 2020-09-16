package com.docclient.exceptionhandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private HttpStatus status;
    private String errorMessage;
    private Throwable cause;

    public ErrorMessage(HttpStatus status){
        this.status = status;
    }

    public ErrorMessage(HttpStatus status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }
    public ErrorMessage(HttpStatus status, String errorMessage, Throwable ex){
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
