package com.sar.ws.service;

import com.sar.ws.shared.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {


    RestaurantDto create(RestaurantDto restaurant);

    RestaurantDto getById(Long id);

    RestaurantDto update(Long id, RestaurantDto restaurantDto);

    void delete(Long id);

    List<RestaurantDto> getRestaurants(int page, int limit);
}
