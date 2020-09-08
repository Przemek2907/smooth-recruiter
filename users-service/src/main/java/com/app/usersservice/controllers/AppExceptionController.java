package com.app.usersservice.controllers;

import com.app.usersservice.dto.ResponseData;
import com.app.usersservice.exceptions.UserServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionController {
    @ExceptionHandler(UserServiceException.class)
    public ResponseData handleUserServiceException(UserServiceException e) {
        return ResponseData
                .builder()
                .error(e.getMessage())
                .build();
    }

}
