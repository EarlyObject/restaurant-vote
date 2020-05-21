package com.earlyobject.ws.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1646468409628182440L;

    public NotFoundException(String message) {
        super(message);
    }
}
