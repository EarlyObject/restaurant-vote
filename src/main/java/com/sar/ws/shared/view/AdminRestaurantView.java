package com.sar.ws.shared.view;

public interface AdminRestaurantView extends JPAProjection {

    long getId();

    String getName();

    String getAddress();

    void setId(long id);

    void setName(String name);

    void setAddress(String address);
}
