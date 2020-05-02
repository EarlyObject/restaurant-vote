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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sar.ws.MealTestData.DATE;
import static com.sar.ws.MealTestData.MEAL1;
import static com.sar.ws.RestaurantTestData.RESTAURANT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MealServiceImplTest extends AbstractServiceTest{

    MealDto mealDto;

    @BeforeEach
    void setUp() {
        mealDto = new MealDto();
        BeanUtils.copyProperties(MEAL1, mealDto);
    }

    @Test
    void create() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(RESTAURANT));
        when(mealRepository.save(any())).thenReturn(MEAL1);
        MealDto returnValue = mealService.create(mealDto, 1L);
        assertNotNull(returnValue);
        assertEquals(returnValue.getDate(), MEAL1.getDate());
        assertEquals(returnValue.getDescription(), MEAL1.getDescription());
        assertEquals(returnValue.getId(), MEAL1.getId());
        assertEquals(returnValue.getPrice(), MEAL1.getPrice());
    }

    @Test
    void getById() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        MealView mealView = factory.createProjection(MealView.class);
        mealView.setId(3000L);
        mealView.setDate(DATE);
        mealView.setDescription("testMeal");
        mealView.setPrice(10.00);
        when(mealRepository.getById(anyLong())).thenReturn(Optional.of(mealView));
        MealView returnValue = mealService.getById(1L);
        assertNotNull(returnValue);
        assertEquals(returnValue.getId(), mealView.getId());
        assertEquals(returnValue, mealView);
    }

    @Test
    void getById_MealServiceException() {
        when(mealRepository.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(MealServiceException.class, ()-> mealService.getById(1L));
    }

    @Test
    void update() {
        Meal meal = new Meal();
        when(mealRepository.findById(anyLong())).thenReturn(Optional.of(meal));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(RESTAURANT));
        when(mealRepository.save(any())).thenReturn(MEAL1);

        MealDto returnValue = mealService.update(1L, mealDto, 4444L);

        assertNotNull(meal);
        assertEquals(MEAL1.getDate(), meal.getDate());
        assertEquals(MEAL1.getDescription(), meal.getDescription());
        assertEquals(MEAL1.getPrice(), meal.getPrice());
        assertEquals(4444L, meal.getRestaurantId());

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
    }

    @Test
    void getFiltered() {
        List<MealView> mealViews = new ArrayList<>();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        MealView mealView = factory.createProjection(MealView.class);
        MealView mealView2 = factory.createProjection(MealView.class);
        MealView mealView3 = factory.createProjection(MealView.class);
        mealViews.add(mealView);
        mealViews.add(mealView2);
        mealViews.add(mealView3);
        Pageable pageable = PageRequest.of(1, 10);
        when(mealRepository.getByRestaurantId(anyLong(), any())).thenReturn(mealViews);
        when(mealRepository.getByRestaurantIdAndDateIsAfter(anyLong(), any(), any())).thenReturn(mealViews);
        when(mealRepository.getByRestaurantIdAndDateIsBefore(anyLong(), any(), any())).thenReturn(mealViews);
        when(mealRepository.getByRestaurantIdAndDateIsBetween(anyLong(), any(), any(), any())).thenReturn(mealViews);

        List<MealView> returnValue = mealService.getFiltered(1L, DATE, DATE, pageable);
        assertNotNull(returnValue);
        assertEquals(3, returnValue.size());
    }
}