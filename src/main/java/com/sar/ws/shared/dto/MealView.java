package com.sar.ws.shared.dto;

import java.time.LocalDate;

public interface MealView {
    long getId();

    LocalDate getDate();

    String getDescription();

    Double getPrice();
}
