package com.sar.ws.shared.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RestaurantDto implements Serializable {
    private static final long serialVersionUID = 7024178261305933055L;

    private long id;

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name can not be blank")
    @Size(min = 1, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address can not be blank")
    @Size(min = 2, message = "Address too short")
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
