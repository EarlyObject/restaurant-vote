package com.sar.ws.shared.view;

import java.time.LocalDate;

public interface MealView {
    long getId();

    LocalDate getDate();

    String getDescription();

    Double getPrice();

    long getRestaurantId();

    void setId(long id);

    void setDate(LocalDate date);

    void setDescription(String description);

    void setPrice(Double price);

    void setRestaurantId(long id);
}
