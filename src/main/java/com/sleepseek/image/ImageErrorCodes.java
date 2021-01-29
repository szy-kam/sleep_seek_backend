package com.sleepseek.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ImageErrorCodes {

    URL_NULL("url is null"),
    FILE_NULL("file is null"),
    USER_NULL("user is null");


    @Getter
    private final String message;
}
