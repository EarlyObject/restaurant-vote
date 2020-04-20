package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

/*
     @Query("select restaurant.id, restaurant.name from Restaurant restaurant")
    Page<Restaurant> restaurantPage();*/

//    @Query("select restaurant.id, restaurant.name from Restaurant restaurant where restaurant.id = :id")
    @Query("select restaurant from Restaurant restaurant where restaurant.id = :id")
    Restaurant  singleRestaurant(@Param("id") long id);

    @Query("select r from Restaurant r")
    Page<Restaurant> findAllWithoutMeals(Pageable pageable);

   /* @Query("select r from Restaurant r left join meals on r.id = meals.restaurant.id")
    Page<Restaurant> findAllWithMeals(Pageable pageable);*/
}
