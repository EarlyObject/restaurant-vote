package com.earlyobject.ws.shared.view;

public interface UserView {

    String getUserId();

    String getFirstName();

    String getLastName();

    String getEmail();

    /**
     * by Ardak Sydyknazar:
     * This setters added in order to test UserController methods
     * https://stackoverflow.com/questions/47258103/mock-projection-result-spring-data-jpa
     */
    void setUserId(String userId);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setEmail(String email);

}
