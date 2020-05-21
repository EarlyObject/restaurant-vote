package com.earlyobject.ws.exceptions;

public class DuplicateEntryException extends RuntimeException {
    private static final long serialVersionUID = -6910487470554372389L;

    public DuplicateEntryException(String message) {
        super(message);
    }
}