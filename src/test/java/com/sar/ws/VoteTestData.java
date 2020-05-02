package com.sar.ws;

import com.sar.ws.io.entity.Vote;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.sar.ws.MealTestData.DATE;
import static com.sar.ws.RestaurantTestData.RESTAURANT_ID;

public class VoteTestData {
    public static final LocalTime TIME = LocalTime.of(11, 00, 00);
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(DATE, TIME);
    public static final Vote VOTE = new Vote(1000L, RESTAURANT_ID, DATE_TIME);
}
