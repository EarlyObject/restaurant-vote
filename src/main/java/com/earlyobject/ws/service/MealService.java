package com.earlyobject.ws.service;

import com.earlyobject.ws.shared.dto.MealDto;
import com.earlyobject.ws.shared.view.MealView;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    MealDto create(MealDto mealDto);

    MealView get(long id);

    MealDto update(long id, MealDto mealDto);

    void delete(long id);

    List<MealView> getAll(int page, int limit);

    List<MealView> getFiltered(long restauranId, LocalDate start, LocalDate end, Pageable pageable);
}
