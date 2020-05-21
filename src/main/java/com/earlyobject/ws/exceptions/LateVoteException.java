package com.earlyobject.ws.exceptions;

public class LateVoteException extends RuntimeException {
    private static final long serialVersionUID = -2887949582001619615L;

    public LateVoteException(String message) {
        super(message);
    }
}
