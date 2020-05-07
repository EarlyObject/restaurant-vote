package com.earlyobject.ws;

import com.earlyobject.ws.entity.Meal;
import com.earlyobject.ws.shared.view.MealView;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDate;

public class MealTestData {
    public static final LocalDate DATE = LocalDate.of(2020, 4, 30);
    public static final LocalDate CURRENT_DATE = LocalDate.now();

    public static final Meal MEAL1 = new Meal(1016, CURRENT_DATE, "Лагман", 20.00, 1007);
    public static final Meal MEAL2 = new Meal(1017, CURRENT_DATE, "Блинчики с мясом", 25.00, 1007);
    public static final Meal MEAL3 = new Meal(1018, CURRENT_DATE, "Бастурма", 5.00, 1008);
    public static final Meal MEAL4 = new Meal(1019, CURRENT_DATE, "Шашлык", 50.00, 1009);

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
