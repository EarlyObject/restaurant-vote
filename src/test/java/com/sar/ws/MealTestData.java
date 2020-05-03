package com.sar.ws;

import com.sar.ws.io.entity.Meal;
import com.sar.ws.shared.view.MealView;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDate;

public class MealTestData {
    public static final LocalDate DATE = LocalDate.of(2020, 4, 30);

    public static final Meal MEAL1 = new Meal(2000, DATE, "Hamburger", 20.00, 1010);
    public static final Meal MEAL2 = new Meal(2001, DATE, "Salad", 15.00, 1010);
    public static final Meal MEAL3 = new Meal(2002, DATE, "Juice", 10.00, 1010);

    public static MealView getMealView() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        MealView mealView = factory.createProjection(MealView.class);
        mealView.setId(2000L);
        mealView.setDate(DATE);
        mealView.setDescription("testMealView");
        mealView.setPrice(10.00);
        return mealView;
    }
}
