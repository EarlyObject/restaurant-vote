package com.sar.ws;

import com.sar.ws.io.entity.Meal;

import java.time.LocalDate;

public class MealTestData {
    public static final LocalDate DATE = LocalDate.of(2020, 4, 30);
    public static final long MEAL_ID = 3333;
    public static final String MEAL_DESCRIPTION = "Test meal description";

    public static final Meal MEAL1 = new Meal(2000, DATE, "Hamburger", 20.00, 1010);
    public static final Meal MEAL2 = new Meal(2001, DATE, "Salad", 15.00, 1010);
    public static final Meal MEAL3 = new Meal(2002, DATE, "Juice", 10.00, 1010);
}
