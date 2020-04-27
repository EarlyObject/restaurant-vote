package com.sar.ws.shared.view;

import java.time.LocalDate;

public interface MealView {
    long getId();

    LocalDate getDate();

    String getDescription();

    Double getPrice();

    long getRestaurantId();
}
