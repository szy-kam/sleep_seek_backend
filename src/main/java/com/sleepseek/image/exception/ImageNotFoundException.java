package com.sleepseek.image.exception;

public class ImageNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Image not found with given url: ";

    public ImageNotFoundException(String url) {
        super(MESSAGE + url);
    }
}
