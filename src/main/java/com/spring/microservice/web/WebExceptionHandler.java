package com.spring.microservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * Exception Handler.
 *
 * @author Alexander Huba
 */
@ControllerAdvice("com.spring.microservice.web")
public class WebExceptionHandler {
    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex NoSuchElementException instance
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
