package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Meal;
import com.sar.ws.io.entity.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, Long> {
}
