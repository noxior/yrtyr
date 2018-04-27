package com.app.contact_loader.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ContactLoaderExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String NOT_FOUND_EXCEPTION = "There is no filter matches.";
    private static final String DB_EXCEPTION = "Database error.";
    private static final String NULLPOINTEREXCEPTION_MSG = "Sorry, Something Went Wrong: Please try again later.";
    private static final String EXCEPTION_MSG = "Error occurred.";
    private static final String SERVLET_PARAMETR_EXCEPTION = "Required String parameter 'nameFilter' is not present";




    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleRequestPfghfghfarametrException(MissingServletRequestParameterException ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, SERVLET_PARAMETR_EXCEPTION, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}