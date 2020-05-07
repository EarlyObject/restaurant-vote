package com.earlyobject.ws.io.repositories;

import com.earlyobject.ws.entity.Meal;
import com.earlyobject.ws.shared.view.MealView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, Long> {

    Optional<MealView> getById(long id);

    List<MealView> getAllBy(Pageable pageable);

    List<MealView> getByRestaurantId(long restaurantId, Pageable pageable);

    List<MealView> getByRestaurantIdAndDateIsBetween(long restaurantId, LocalDate start, LocalDate end, Pageable pageable);

    List<MealView> getByRestaurantIdAndDateIsBefore(long restaurantId, LocalDate end, Pageable pageable);

    List<MealView> getByRestaurantIdAndDateIsAfter(long restaurantId, LocalDate start, Pageable pageable);
}
