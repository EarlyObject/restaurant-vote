package com.sar.ws.shared.view;

public interface AdminRestaurantView extends JPAProjection {

    long getId();

    String getName();

    String getAddress();
}
