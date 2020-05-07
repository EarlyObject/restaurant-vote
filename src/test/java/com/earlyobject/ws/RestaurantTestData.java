package com.earlyobject.ws;

import com.earlyobject.ws.entity.Meal;
import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.entity.Vote;
import com.earlyobject.ws.shared.view.AdminRestaurantView;
import com.earlyobject.ws.shared.view.RestaurantView;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.*;

import static com.earlyobject.ws.MealTestData.*;

public class RestaurantTestData {
    public static final long RESTAURANT_ID = 1007L;
    public static final String RESTAURANT_NAME = "Белая устрица";
    public static final String RESTAURANT_ADDRESS = "Москва, проспект Маяковского 9";
    public static final List<Meal> MEALS = new ArrayList<>(Arrays.asList(MEAL1, MEAL2));
    public static final Set<Vote> VOTES = new HashSet<>(Collections.singletonList(VoteTestData.VOTE));

    public static Restaurant getNewRestaurant() {
        Restaurant dummyRestaurant = new Restaurant();
        dummyRestaurant.setId(RESTAURANT_ID);
        dummyRestaurant.setName(RESTAURANT_NAME);
        dummyRestaurant.setAddress(RESTAURANT_ADDRESS);
        dummyRestaurant.setMeals(MEALS);
        dummyRestaurant.setVotes(VOTES);
//        dummyRestaurant.setVotesCount(VOTES.size());
        return dummyRestaurant;
    }

    public static AdminRestaurantView getAdminRestaurantView() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        AdminRestaurantView adminRestaurantView = factory.createProjection(AdminRestaurantView.class);
        adminRestaurantView.setId(RESTAURANT_ID);
        adminRestaurantView.setName(RESTAURANT_NAME);
        adminRestaurantView.setAddress(RESTAURANT_ADDRESS);
        return adminRestaurantView;
    }

    public static RestaurantView getRestaurantView() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        RestaurantView restaurantView = factory.createProjection(RestaurantView.class);
        restaurantView.setId(RESTAURANT_ID);
        restaurantView.setName(RESTAURANT_NAME);
        restaurantView.setAddress(RESTAURANT_ADDRESS);
        return restaurantView;
    }
}
