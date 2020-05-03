package com.sar.ws.service.impl;

import com.sar.ws.exceptions.MealServiceException;
import com.sar.ws.io.entity.Meal;
import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.shared.view.MealView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sar.ws.MealTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MealServiceImplTest extends AbstractServiceTest {

    MealDto mealDto;

    @BeforeEach
    void setUp() {
        mealDto = new MealDto();
        BeanUtils.copyProperties(MEAL1, mealDto);
    }

    @Test
    void create() {
        when(restaurantRepository.existsById(anyLong())).thenReturn(true);
        when(mealRepository.save(any())).thenReturn(MEAL1);
        MealDto returnValue = mealService.create(mealDto);
        assertNotNull(returnValue);
        assertEquals(returnValue.getDate(), MEAL1.getDate());
        assertEquals(returnValue.getDescription(), MEAL1.getDescription());
        assertEquals(returnValue.getId(), MEAL1.getId());
        assertEquals(returnValue.getPrice(), MEAL1.getPrice());
    }

    @Test
    void getById() {
        MealView mealView = getMealView();
        when(mealRepository.getById(anyLong())).thenReturn(Optional.of(mealView));
        MealView returnValue = mealService.getById(1L);
        assertNotNull(returnValue);
        assertEquals(returnValue.getId(), mealView.getId());
        assertEquals(returnValue, mealView);
    }

    @Test
    void getById_MealServiceException() {
        when(mealRepository.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(MealServiceException.class, () -> mealService.getById(1L));
    }

    @Test
    void update() {
        Meal meal = new Meal();
        when(mealRepository.findById(anyLong())).thenReturn(Optional.of(meal));
        when(restaurantRepository.existsById(anyLong())).thenReturn(true);
        when(mealRepository.save(any())).thenReturn(MEAL1);

        MealDto returnValue = mealService.update(1L, mealDto);

        assertNotNull(meal);
        assertEquals(MEAL1.getDate(), meal.getDate());
        assertEquals(MEAL1.getDescription(), meal.getDescription());
        assertEquals(MEAL1.getPrice(), meal.getPrice());
        assertEquals(MEAL1.getRestaurantId(), meal.getRestaurantId());

        assertNotNull(returnValue);
        assertEquals(MEAL1.getDate(), returnValue.getDate());
        assertEquals(MEAL1.getDescription(), returnValue.getDescription());
        assertEquals(MEAL1.getPrice(), returnValue.getPrice());
        assertEquals(MEAL1.getId(), returnValue.getId());
    }

    @Test
    void delete() {
        ArgumentCaptor<Meal> arg = ArgumentCaptor.forClass(Meal.class);
        mealRepository.delete(MEAL1);
        verify(mealRepository).delete(arg.capture());
        assertEquals(MEAL1, arg.getValue());
    }

    @Test
    void getAll() {
        MealView mealView = getMealView();
        List<MealView> mealViews = new ArrayList<>(Arrays.asList(mealView, mealView));
        when(mealRepository.getAllBy(any())).thenReturn(mealViews);
        List<MealView> returnValue = mealService.getAll(1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
    }

    @Test
    void getFiltered() {
        MealView mealView = getMealView();
        List<MealView> mealViews = new ArrayList<>(Arrays.asList(mealView, mealView, mealView));
        Pageable pageable = PageRequest.of(1, 10);
        when(mealRepository.getByRestaurantId(anyLong(), any())).thenReturn(mealViews);

        List<MealView> returnValue = mealService.getFiltered(1L, null, null, pageable);
        assertNotNull(returnValue);
        assertEquals(3, returnValue.size());
    }

    @Test
    void getFiltered_DateIsBefore() {
        MealView mealView = getMealView();
        List<MealView> mealViews = new ArrayList<>(Arrays.asList(mealView, mealView, mealView));
        Pageable pageable = PageRequest.of(1, 10);
        when(mealRepository.getByRestaurantIdAndDateIsBefore(anyLong(), any(), any())).thenReturn(mealViews);

        List<MealView> returnValue = mealService.getFiltered(1L, null, DATE, pageable);
        assertNotNull(returnValue);
        assertEquals(3, returnValue.size());
    }

    @Test
    void getFiltered_DateIsAfter() {
        MealView mealView = getMealView();
        List<MealView> mealViews = new ArrayList<>(Arrays.asList(mealView, mealView, mealView));
        Pageable pageable = PageRequest.of(1, 10);
        when(mealRepository.getByRestaurantIdAndDateIsAfter(anyLong(), any(), any())).thenReturn(mealViews);

        List<MealView> returnValue = mealService.getFiltered(1L, DATE, null, pageable);
        assertNotNull(returnValue);
        assertEquals(3, returnValue.size());
    }

    @Test
    void getFiltered_DateIsBetween() {
        MealView mealView = getMealView();
        List<MealView> mealViews = new ArrayList<>(Arrays.asList(mealView, mealView, mealView));
        Pageable pageable = PageRequest.of(1, 10);
        when(mealRepository.getByRestaurantIdAndDateIsBetween(anyLong(), any(), any(), any())).thenReturn(mealViews);

        List<MealView> returnValue = mealService.getFiltered(1L, DATE, DATE, pageable);
        assertNotNull(returnValue);
        assertEquals(3, returnValue.size());
    }
}