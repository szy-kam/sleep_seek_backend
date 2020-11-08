package com.sleepseek.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(String.format("User %s not found", email));
    }

    public UserNotFoundException(Long id){
        super(String.format("User %s not found", id));
    }
}
