package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

//    List<Restaurant> getRestaurantsF. findAllAndMealDate(LocalDate date);
}
