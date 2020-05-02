package com.sar.ws.service.impl;

import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.AdminRestaurantView;
import com.sar.ws.shared.view.JPAProjection;
import com.sar.ws.shared.view.MealView;
import com.sar.ws.shared.view.RestaurantView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sar.ws.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantServiceImplTest extends AbstractServiceTest{

    RestaurantDto restaurantDto;
    Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurantDto = new RestaurantDto();
        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setAddress(RESTAURANT_ADDRESS);
        restaurantDto.setName(RESTAURANT_NAME);

        restaurant = new Restaurant();
        restaurant.setName(RESTAURANT_NAME);
        restaurant.setAddress(RESTAURANT_ADDRESS);
        restaurant.setId(RESTAURANT_ID);
        restaurant.setMeals(MEALS);
    }

    @Test
    void create() {
        when(restaurantRepository.findAllById(any())).thenReturn(null);
        when(restaurantRepository.save(any())).thenReturn(restaurant);
        RestaurantDto returnValue = restaurantService.create(restaurantDto);
        assertNotNull(returnValue);
        assertEquals(returnValue.getId(), restaurant.getId());
        assertEquals(returnValue.getAddress(), restaurant.getAddress());
        assertEquals(returnValue.getName(), restaurant.getName());
    }

    @Test
    void getById() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        AdminRestaurantView adminRestaurantView = factory.createProjection(AdminRestaurantView.class);
        adminRestaurantView.setId(RESTAURANT_ID);
        adminRestaurantView.setName(RESTAURANT_NAME);
        adminRestaurantView.setAddress(RESTAURANT_ADDRESS);
        when(restaurantRepository.getAdminRestaurantViewById(anyLong())).thenReturn(Optional.of(adminRestaurantView));
        JPAProjection returnValue = restaurantService.getById(1L, false);
        assertNotNull(returnValue);
        assertEquals(returnValue, adminRestaurantView);
    }

    @Test
    void getByIdLoadAll() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        RestaurantView restaurantView = factory.createProjection(RestaurantView.class);
        restaurantView.setId(RESTAURANT_ID);
        restaurantView.setName(RESTAURANT_NAME);
        restaurantView.setAddress(RESTAURANT_ADDRESS);
        restaurantView.setVotesCount(7);
        List<MealView> mealViewList = new ArrayList<>();
        restaurantView.setMeals(mealViewList);
        when(restaurantRepository.getById(anyLong(), any())).thenReturn(Optional.of(restaurantView));
        RestaurantView returnValue = (RestaurantView) restaurantService.getById(1L, true);
        assertNotNull(returnValue);
        assertEquals(returnValue, restaurantView);
        assertEquals(0, returnValue.getMeals().size());
        assertEquals(7, returnValue.getVotesCount());
    }

    @Test
    void get_RestaurantServiceException() {
        when(restaurantRepository.getById(anyLong(), any())).thenReturn(null);
        assertThrows(RestaurantServiceException.class, ()-> restaurantService.getById(1L, false));
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
        restaurantRepository.delete(RESTAURANT);
        verify(restaurantRepository).delete(arg.capture());
        assertEquals(RESTAURANT, arg.getValue());
    }

    /*
    @Test
    void getAll() {
    }*/
}