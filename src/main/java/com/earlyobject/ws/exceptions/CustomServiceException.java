package com.earlyobject.ws.exceptions;

public class CustomServiceException extends RuntimeException {
    private static final long serialVersionUID = 6524440028254053905L;

    public CustomServiceException(String message) {
        super(message);
    }
}
