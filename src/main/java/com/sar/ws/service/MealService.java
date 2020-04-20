package com.sar.ws.service;

import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.shared.dto.MealView;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MealService {

    MealDto create(MealDto mealDto);

//    MealDto get(long id);

    MealDto update(long id, MealDto mealDto);

    void delete(long id);

    @Transactional(readOnly = true)
    List<MealView> getMeals(int page, int limit);

    @Transactional
    MealView getById(long id);

}
