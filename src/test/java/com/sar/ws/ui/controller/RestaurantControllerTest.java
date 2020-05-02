package com.sar.ws.ui.controller;

import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.ui.model.request.RestaurantDetailsRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.sar.ws.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantService restaurantService;

    RestaurantDto restaurantDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        restaurantDto = new RestaurantDto();
        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setAddress(RESTAURANT_ADDRESS);
    }

    @Test
    void create() throws Exception {
        RestaurantDetailsRequestModel restaurantModel = new RestaurantDetailsRequestModel();
        when(restaurantService.create(any())).thenReturn(restaurantDto);
        RestaurantDto returnValue = restaurantController.create(restaurantModel);
        assertNotNull(returnValue);
        assertEquals(restaurantDto.getId(), returnValue.getId());
        assertEquals(restaurantDto.getName(), returnValue.getName());
        assertEquals(restaurantDto.getAddress(), returnValue.getAddress());
    }

    @Test
    void get() {
    }

    @Test
    void getFiltered() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}