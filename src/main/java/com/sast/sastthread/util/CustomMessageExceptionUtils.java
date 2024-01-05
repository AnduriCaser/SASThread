package com.sast.sastthread.util;

import org.springframework.http.HttpStatus;

import com.sast.sastthread.exception.CustomMessageException;

public class CustomMessageExceptionUtils {
    private CustomMessageExceptionUtils() {
    }

    public static CustomMessageException unauthorized() {
        CustomMessageException messageException = new CustomMessageException();
        messageException.setMessage("Unauthorized");
        messageException.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return messageException;
    }
}
