package com.sar.ws.shared.dto;

import java.io.Serializable;
import java.util.List;

public class RestaurantDto implements Serializable {
    private static final long serialVersionUID = 7024178261305933055L;
    private long id;
    private String name;
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
