package com.sar.ws.service;

import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.shared.view.MealView;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    MealDto create(MealDto mealDto, long restaurantId);

    MealView getById(long id);

    MealDto update(long id, MealDto mealDto, long restaurantId);

    void delete(long id);

    List<MealView> getAll(int page, int limit);

    List<MealView> getFiltered(long restauranId, LocalDate start, LocalDate end, Pageable pageable);
}
