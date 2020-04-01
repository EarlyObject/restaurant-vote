package com.sar.ws.service;

import com.sar.ws.shared.dto.MealDto;

import java.util.List;

public interface MealService {
    MealDto create(MealDto mealDto);

    MealDto get(long id);

    MealDto update(long id, MealDto mealDto);

    void delete(long id);

    List<MealDto> getMeals(int page, int limit);
}
