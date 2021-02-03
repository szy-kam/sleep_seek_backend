package com.sleepseek.reservation;

import com.sleepseek.common.ApiError;
import com.sleepseek.reservation.exception.ReservationConflictException;
import com.sleepseek.reservation.exception.ReservationNotFoundException;
import com.sleepseek.reservation.exception.ReservationValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(ReservationValidationException.class)
    ResponseEntity<?> handleReservationValidationException(ReservationValidationException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Reservation is invalid", exception));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    ResponseEntity<?> handleReservationNotFoundException(ReservationNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Reservation not found", exception));
    }


    @ExceptionHandler(ReservationConflictException.class)
    ResponseEntity<?> handleReservationConflictException(ReservationConflictException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Reservation conflict", exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
