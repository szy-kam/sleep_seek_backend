package com.sleepseek.image;

import com.sleepseek.common.ApiError;
import com.sleepseek.image.exception.ImageStorageException;
import com.sleepseek.image.exception.ImageValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(ImageStorageException.class)
    public ResponseEntity<?> handleImageStorageException(ImageStorageException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Image storage error", exception));
    }

    @ExceptionHandler(ImageValidationException.class)
    public ResponseEntity<?> handleImageValidationException(ImageValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Image is invalid", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
