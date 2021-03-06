package com.sleepseek.image;

import com.sleepseek.common.ApiError;
import com.sleepseek.image.exception.ImageNotFoundException;
import com.sleepseek.image.exception.ImageStorageException;
import com.sleepseek.image.exception.ImageValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ImageExceptionHandler {

    @ExceptionHandler(ImageStorageException.class)
    ResponseEntity<?> handleImageStorageException(ImageStorageException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Image storage error", exception));
    }

    @ExceptionHandler(ImageValidationException.class)
    ResponseEntity<?> handleImageValidationException(ImageValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Image is invalid", exception));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    ResponseEntity<?> handleImageNotFoundException(ImageNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Image not found", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
