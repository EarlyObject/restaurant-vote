package com.sar.ws.service;

import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.RestaurantView;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantService {

    RestaurantDto create(RestaurantDto restaurant);

    RestaurantView getById(Long id);

    RestaurantDto update(Long id, RestaurantDto restaurantDto);

    void delete(Long id);

    List<RestaurantView> getAll(int page, int limit);
}
