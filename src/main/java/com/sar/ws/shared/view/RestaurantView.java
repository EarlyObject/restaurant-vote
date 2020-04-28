package com.sar.ws.shared.view;

import java.util.List;

public interface RestaurantView extends JPAProjection {

    long getId();

    String getName();

    String getAddress();

    Integer getVotesCount();

    List<MealView> getMeals();
}
