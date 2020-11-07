package com.sleepseek.stay.exception;

public class StayNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Stay with given ID not found";

    public StayNotFoundException(){
        super(MESSAGE);
    }
    public StayNotFoundException(Long id){
        super(MESSAGE + ", id: " + id.toString());
    }
}
