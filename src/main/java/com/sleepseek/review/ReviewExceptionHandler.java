package com.sleepseek.review;

import com.sleepseek.common.ApiError;
import com.sleepseek.review.exceptions.ReviewNotFoundException;
import com.sleepseek.review.exceptions.ReviewValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ReviewExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ReviewValidationException.class)
    ResponseEntity<?> handleReviewValidationException(ReviewValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Review is invalid", exception));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    ResponseEntity<?> handleReviewNotFoundException(ReviewNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Review not found", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
