package com.earlyobject.ws.service.impl.integrationTests;

import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.io.repositories.RestaurantRepository;
import com.earlyobject.ws.service.impl.RestaurantServiceImpl;
import com.earlyobject.ws.shared.dto.RestaurantDto;
import com.earlyobject.ws.shared.view.AdminRestaurantView;
import com.earlyobject.ws.shared.view.JPAProjection;
import com.earlyobject.ws.shared.view.RestaurantView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.earlyobject.ws.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    RestaurantServiceImpl restaurantService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void create() {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName("RESTAURANT_NAME");
        restaurantDto.setAddress("RESTAURANT_ADDRESS");
        RestaurantDto savedRestaurant = restaurantService.create(restaurantDto);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(savedRestaurant.getId());
        assertTrue(optionalRestaurant.isPresent());
        Restaurant restaurant = optionalRestaurant.get();
        assertEquals("RESTAURANT_NAME", restaurant.getName());
        assertEquals("RESTAURANT_ADDRESS", restaurant.getAddress());
    }

    @Test
    void create_ConstraintViolationException() {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setAddress(RESTAURANT_ADDRESS);
        assertThrows(DataIntegrityViolationException.class, () -> restaurantService.create(restaurantDto));
    }

    @Test
    void get() {
        AdminRestaurantView projection = restaurantService.get(RESTAURANT_ID, false);
        assertEquals(RESTAURANT_ID, projection.getId());
        assertEquals(RESTAURANT_ADDRESS, projection.getAddress());
        assertEquals(RESTAURANT_NAME, projection.getName());
    }

    @Test
    void get_loadAll() {
        RestaurantView projection = restaurantService.get(RESTAURANT_ID, true);
        assertEquals(RESTAURANT_ID, projection.getId());
        assertEquals(RESTAURANT_ADDRESS, projection.getAddress());
        assertEquals(RESTAURANT_NAME, projection.getName());
        assertNotNull(projection.getMeals());
        assertEquals(2, projection.getMeals().size());
        assertEquals(1016, projection.getMeals().get(0).getId());
        assertEquals(MEALS.get(0).getRestaurantId(), projection.getMeals().get(0).getRestaurantId());
        assertEquals(MEALS.get(0).getDate(), projection.getMeals().get(0).getDate());
        assertEquals(MEALS.get(0).getPrice(), projection.getMeals().get(0).getPrice());
        assertEquals(MEALS.get(0).getDescription(), projection.getMeals().get(0).getDescription());
    }


    @Test
    void update() {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setAddress("DIFFERENT_RESTAURANT_ADDRESS");
        RestaurantDto updatedRestaurant = restaurantService.update(1007L, restaurantDto);
        assertNotNull(updatedRestaurant);
        assertEquals(1007, updatedRestaurant.getId());
        assertEquals(RESTAURANT_NAME, updatedRestaurant.getName());
        assertEquals("DIFFERENT_RESTAURANT_ADDRESS", updatedRestaurant.getAddress());
    }

    @Test
    void delete() {
        restaurantService.delete(1007L);
        assertThrows(CustomServiceException.class, () -> restaurantService.get(1007L, false));
    }

    @Test
    void getAll() {
        List<? extends JPAProjection> all = restaurantService.getAll(0, 10, false);
        assertNotNull(all);
        assertEquals(3, all.size());
    }
}