package com.earlyobject.ws.ui.model.response;

public enum ErrorMessages {

    NO_RECORD_FOUND("Record with provided id is not found"),
    DUPLICATE_RECORD("Record already exists"),
    DUPLICATE_VOTE("You have already voted today"),
    NO_VOTE("You have not voted today"),
    LATE_VOTE("It is too late to change your vote");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
