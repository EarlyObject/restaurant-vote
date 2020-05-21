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

@Transactional
@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, Long> {

    @Transactional(readOnly = true)
    Optional<MealView> getById(long id);

    @Transactional(readOnly = true)
    List<MealView> getAllBy(Pageable pageable);

    @Transactional(readOnly = true)
    List<MealView> getByRestaurantId(long restaurantId, Pageable pageable);

    @Transactional(readOnly = true)
    List<MealView> getByRestaurantIdAndDateIsBetween(long restaurantId, LocalDate start, LocalDate end, Pageable pageable);

    @Transactional(readOnly = true)
    List<MealView> getByRestaurantIdAndDateIsBefore(long restaurantId, LocalDate end, Pageable pageable);

    @Transactional(readOnly = true)
    List<MealView> getByRestaurantIdAndDateIsAfter(long restaurantId, LocalDate start, Pageable pageable);
}
