package com.sar.ws.shared.view;

import java.util.List;

public interface RestaurantView {
    long getId();

    String getName();

    String getAddress();

    String getPhoneNumber();

    List<MealView> getMeals();

    Integer getVotesCount();
}
