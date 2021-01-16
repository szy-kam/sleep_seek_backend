package com.sleepseek.review.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Review not found with given ID: ";

    public ReviewNotFoundException(Long id) {
        super(MESSAGE + id.toString());
    }
}
