package com.earlyobject.ws.service;

import com.earlyobject.ws.shared.dto.RestaurantDto;
import com.earlyobject.ws.shared.view.JPAProjection;

import java.util.List;

public interface RestaurantService {

    RestaurantDto create(RestaurantDto restaurant);

    <T extends JPAProjection> T get(Long id, boolean loadAll);

    RestaurantDto update(Long id, RestaurantDto restaurantDto);

    void delete(Long id);

    List<? extends JPAProjection> getAll(int page, int limit, boolean loadAll);
}
