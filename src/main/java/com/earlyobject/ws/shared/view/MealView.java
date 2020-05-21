package com.earlyobject.ws.shared.view;

import java.time.LocalDate;

public interface MealView {
    long getId();

    LocalDate getDate();

    String getDescription();

    int getPrice();

    long getRestaurantId();

    void setId(long id);

    void setDate(LocalDate date);

    void setDescription(String description);

    void setPrice(int price);

    void setRestaurantId(long id);
}
