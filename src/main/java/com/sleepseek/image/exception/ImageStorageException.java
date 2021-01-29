package com.sleepseek.image.exception;

public class ImageStorageException extends RuntimeException {
    private final static String MESSAGE = "Image storage exception ";

    public ImageStorageException(String id) {
        super(MESSAGE + id);
    }
}
