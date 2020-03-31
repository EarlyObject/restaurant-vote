package com.sar.ws.exceptions;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = -1820714076918506796L;

    public UserServiceException(String message) {
        super(message);
    }
}
