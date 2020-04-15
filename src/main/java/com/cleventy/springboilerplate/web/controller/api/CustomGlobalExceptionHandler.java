package com.cleventy.springboilerplate.web.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cleventy.springboilerplate.business.services.exceptions.BadRequestException;
import com.cleventy.springboilerplate.business.services.exceptions.DuplicateInstanceException;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.exceptions.InternalErrorException;
import com.cleventy.springboilerplate.business.services.exceptions.NoPermissionException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InstanceNotFoundException.class)
    public static void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
    
    @ExceptionHandler({BadRequestException.class, DuplicateInstanceException.class})
    public static void springHandleBadRequestError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    
    @ExceptionHandler(InternalErrorException.class)
    public static void springHandleInternalError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
 
    @ExceptionHandler(NoPermissionException.class)
    public static void springHandleNoPermission(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value());
    }

}