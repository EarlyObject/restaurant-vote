package com.sar.ws.service;

import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.JPAProjection;
import com.sar.ws.shared.view.RestaurantView;

import java.util.List;

public interface RestaurantService {

    RestaurantDto create(RestaurantDto restaurant);

    <T extends JPAProjection> T getById(Long id, boolean loadAll);

    RestaurantDto update(Long id, RestaurantDto restaurantDto);

    void delete(Long id);

    List<? extends JPAProjection> getAll(int page, int limit, boolean loadAll);
}
