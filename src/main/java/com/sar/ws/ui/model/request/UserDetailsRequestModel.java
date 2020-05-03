package com.sar.ws.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequestModel {
    public interface InitialValidation{

    }

    @NotNull(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname can not be blank")
    @Size(min = 1, max = 50, message = "Firstname should be between 1 and 50 characters")
    private String firstName;

    @NotNull(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname can not be blank")
    @Size(min = 1, max = 50, message = "Lastname should be between 1 and 50 characters")
    private String lastName;

    @Email(groups = {InitialValidation.class})
    @NotNull(message = "Email is mandatory", groups = {InitialValidation.class})
    private String email;

    @NotNull(message = "Password is mandatory", groups = {InitialValidation.class})
    @NotBlank(message = "Password can not be blank", groups = {InitialValidation.class})
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
