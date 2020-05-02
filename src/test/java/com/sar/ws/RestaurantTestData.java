package com.sar.ws;

import com.sar.ws.io.entity.Meal;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.entity.Vote;

import java.util.*;

import static com.sar.ws.MealTestData.*;

public class RestaurantTestData {
    public static final long RESTAURANT_ID = 2000L;
    public static final String RESTAURANT_ADDRESS = "testAddress";
    public static final String RESTAURANT_NAME = "testRestaurantName";
    public static final List<Meal> MEALS = new ArrayList<>(Arrays.asList(MEAL1, MEAL2, MEAL3));
    public static final Set<Vote> VOTES = new HashSet<>();
    public static final Restaurant RESTAURANT = new Restaurant(getNewRestaurant());

    public static Restaurant getNewRestaurant() {
        Restaurant dummyRestaurant = new Restaurant();
        dummyRestaurant.setId(RESTAURANT_ID);
        dummyRestaurant.setName(RESTAURANT_NAME);
        dummyRestaurant.setAddress(RESTAURANT_ADDRESS);
        dummyRestaurant.setMeals(MEALS);
        dummyRestaurant.setVotes(VOTES);
        dummyRestaurant.setVotesCount(VOTES.size());
        return dummyRestaurant;
    }
}
