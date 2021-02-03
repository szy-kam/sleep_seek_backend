package com.sleepseek.user;

import com.sleepseek.common.ApiError;
import com.sleepseek.user.exception.UserAlreadyExistsException;
import com.sleepseek.user.exception.UserNotFoundException;
import com.sleepseek.user.exception.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "User already exists", exception));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "User not found", exception));
    }

    @ExceptionHandler(value = UserValidationException.class)
    ResponseEntity<?> handleUserValidationException(UserValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "User validation error", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
