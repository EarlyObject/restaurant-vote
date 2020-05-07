package com.earlyobject.ws.shared.view;

import java.util.List;

public interface RestaurantView extends JPAProjection {

    long getId();

    String getName();

    String getAddress();

//    Integer getVotesCount();

    List<MealView> getMeals();

    /**
     * by Ardak Sydyknazar:
     * This setters added in order to make tests
     * https://stackoverflow.com/questions/47258103/mock-projection-result-spring-data-jpa
     */

    void setId(long id);

    void setName(String name);

    void setAddress(String address);

//    void setVotesCount(Integer votesCount);

    void setMeals(List<MealView> mealViews);
}
