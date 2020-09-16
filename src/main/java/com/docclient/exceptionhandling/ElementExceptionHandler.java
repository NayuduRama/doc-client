package com.docclient.exceptionhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ElementExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleServiceException(ServiceException ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getHttpStatus(), ex.getErrorMessage());
        return buildResponseEntity(errorMessage);
    }
    private ResponseEntity<Object> buildResponseEntity(ErrorMessage apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
