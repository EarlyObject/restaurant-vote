package com.earlyobject.ws.service.unitTests;

import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.exceptions.NotFoundException;
import com.earlyobject.ws.shared.dto.RestaurantDto;
import com.earlyobject.ws.shared.view.AdminRestaurantView;
import com.earlyobject.ws.shared.view.JPAProjection;
import com.earlyobject.ws.shared.view.MealView;
import com.earlyobject.ws.shared.view.RestaurantView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.earlyobject.ws.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantServiceTest extends AbstractServiceTest {

    RestaurantDto restaurantDto;
    Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurantDto = new RestaurantDto();
        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setAddress(RESTAURANT_ADDRESS);

        restaurant = getNewRestaurant();
    }

    @Test
    void create() {
        when(restaurantRepository.save(any())).thenReturn(restaurant);
        RestaurantDto returnValue = restaurantService.create(restaurantDto);
        assertNotNull(returnValue);
        assertEquals(returnValue.getId(), restaurant.getId());
        assertEquals(returnValue.getAddress(), restaurant.getAddress());
        assertEquals(returnValue.getName(), restaurant.getName());
    }

    @Test
    void get() {
        AdminRestaurantView adminRestaurantView = getAdminRestaurantView();
        when(restaurantRepository.getAdminRestaurantViewById(anyLong())).thenReturn(Optional.of(adminRestaurantView));
        JPAProjection returnValue = restaurantService.get(1L);
        assertNotNull(returnValue);
        assertEquals(returnValue, adminRestaurantView);
    }

    @Test
    void getWithMeal() {
        RestaurantView restaurantView = getRestaurantView();
        List<MealView> mealViewList = new ArrayList<>();
        restaurantView.setMeals(mealViewList);
        when(restaurantRepository.getById(anyLong(), any())).thenReturn(Optional.of(restaurantView));
        RestaurantView returnValue = restaurantService.getWithMeal(1L);
        assertNotNull(returnValue);
        assertEquals(returnValue, restaurantView);
        assertEquals(0, returnValue.getMeals().size());
    }

    @Test
    void get_RestaurantServiceException() {
        when(restaurantRepository.getById(anyLong(), any())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> restaurantService.get(1L));
    }

    @Test
    void update() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any())).thenReturn(restaurant);

        RestaurantDto returnValue = restaurantService.update(1L, restaurantDto);
        assertNotNull(returnValue);
        assertEquals(returnValue.getName(), restaurantDto.getName());
        assertEquals(returnValue.getAddress(), restaurantDto.getAddress());
        assertEquals(returnValue.getId(), restaurantDto.getId());
    }

    @Test
    void delete() {
        ArgumentCaptor<Restaurant> arg = ArgumentCaptor.forClass(Restaurant.class);
        restaurantRepository.delete(restaurant);
        verify(restaurantRepository).delete(arg.capture());
        assertEquals(restaurant, arg.getValue());
    }

    @Test
    void getAllWithMeals() {
        RestaurantView restaurantView = getRestaurantView();
        List<RestaurantView> restaurantViews = new ArrayList<>();
        restaurantViews.add(restaurantView);
        restaurantViews.add(restaurantView);
        when(restaurantRepository.getAllWithMeals(any(), any())).thenReturn(restaurantViews);
        List<? extends JPAProjection> returnValue = restaurantService.getAllWithMeals(1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
    }

    @Test
    void getAll() {
        AdminRestaurantView adminRestaurantView = getAdminRestaurantView();
        List<AdminRestaurantView> restaurantViews = new ArrayList<>();
        restaurantViews.add(adminRestaurantView);
        restaurantViews.add(adminRestaurantView);
        when(restaurantRepository.getAllBy(any())).thenReturn(restaurantViews);
        List<? extends JPAProjection> returnValue = restaurantService.getAll(1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
    }
}