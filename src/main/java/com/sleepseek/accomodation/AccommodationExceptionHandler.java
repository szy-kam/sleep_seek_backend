package com.sleepseek.accomodation;

import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.accomodation.exception.AccommodationPropertyNotFound;
import com.sleepseek.accomodation.exception.AccommodationValidationException;
import com.sleepseek.common.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccommodationExceptionHandler {

    @ExceptionHandler(AccommodationNotFoundException.class)
    public ResponseEntity<?> handleReviewNotFoundException(AccommodationNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Accommodation not found", exception));
    }

    @ExceptionHandler(AccommodationValidationException.class)
    public ResponseEntity<?> handleAccommodationValidationException(AccommodationValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Accommodation is invalid", exception));
    }

    @ExceptionHandler(AccommodationPropertyNotFound.class)
    public ResponseEntity<?> handleAccommodationPropertyNotFound(AccommodationPropertyNotFound exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Accommodation Property not found", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
