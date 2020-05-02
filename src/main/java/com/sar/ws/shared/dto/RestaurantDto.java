package com.sar.ws.shared.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RestaurantDto implements Serializable {
    private static final long serialVersionUID = 7024178261305933055L;

    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
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
