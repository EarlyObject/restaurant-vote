package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.service.RestaurantService;
import com.earlyobject.ws.shared.dto.RestaurantDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.earlyobject.ws.RestaurantTestData.*;
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
        RestaurantDto restaurantModel = new RestaurantDto();
        when(restaurantService.create(any())).thenReturn(restaurantDto);
        RestaurantDto returnValue = restaurantController.create(restaurantModel);
        assertNotNull(returnValue);
        assertEquals(restaurantDto.getId(), returnValue.getId());
        assertEquals(restaurantDto.getName(), returnValue.getName());
        assertEquals(restaurantDto.getAddress(), returnValue.getAddress());
    }
}