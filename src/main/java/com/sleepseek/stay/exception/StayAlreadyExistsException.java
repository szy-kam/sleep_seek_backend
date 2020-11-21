package com.sleepseek.stay.exception;

public class StayAlreadyExistsException extends RuntimeException {
    private final static String MESSAGE = "Stay with given ID already exists";

    public StayAlreadyExistsException(){
        super(MESSAGE);
    }
    public StayAlreadyExistsException(Long id){
        super(MESSAGE + ", id: " + id.toString());
    }
}
