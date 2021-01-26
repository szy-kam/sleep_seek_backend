package com.sleepseek.stay;

import com.sleepseek.common.ApiError;
import com.sleepseek.stay.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class StayExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = StayAlreadyExistsException.class)
    public ResponseEntity<?> handleStayAlreadyExistsException(StayAlreadyExistsException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Stay already exists", exception));
    }

    @ExceptionHandler(value = StayValidationException.class)
    public ResponseEntity<?> handleStayAlreadyExistsException(StayValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Stay validation error", exception));
    }

    @ExceptionHandler(value = StayNotFoundException.class)
    public ResponseEntity<?> handleStayNotFoundException(StayNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Stay not found", exception));
    }

    @ExceptionHandler(value = StayPropertyNotFoundException.class)
    public ResponseEntity<?> handleStayPropertyNotFoundException(StayPropertyNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Stay property not found", exception));
    }

    @ExceptionHandler(value = StayCategoryNotFoundException.class)
    public ResponseEntity<?> handleStayCategoryNotFoundException(StayCategoryNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Stay category not found", exception));
    }

    @ExceptionHandler(value = StaySearchParametersException.class)
    public ResponseEntity<?> handleStaySearchParametersException(StaySearchParametersException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Stay search parameters error", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
