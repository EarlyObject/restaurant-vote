package com.sar.ws.service.impl;

import com.sar.ws.exceptions.MealServiceException;
import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.RestaurantView;
import com.sar.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDto create(RestaurantDto restaurantDto) {

        if (restaurantRepository.findById(restaurantDto.getId()).isPresent()) {
            throw new RuntimeException("Record already exists");
        }

        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);

        Restaurant storedRestaurant = restaurantRepository.save(restaurant);
        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(storedRestaurant, returnValue);

        return returnValue;
    }

    @Override
    public RestaurantView getById(Long id) {
        Optional<RestaurantView> restaurantViewOptional = restaurantRepository.getById(id);
        if (!restaurantViewOptional.isPresent()) {
            throw new RestaurantServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return restaurantViewOptional.get();
    }

    @Override
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        RestaurantDto returnValue = new RestaurantDto();

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (!restaurantOptional.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }

        Restaurant restaurant = restaurantOptional.get();
        BeanUtils.copyProperties(restaurantDto, restaurant, "id");
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        BeanUtils.copyProperties(updatedRestaurant, returnValue);
        return returnValue;
    }

    @Override
    public void delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (!restaurantOptional.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }
        restaurantRepository.delete(restaurantOptional.get());
    }

    @Override
    public List<RestaurantView> getAll(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<RestaurantView> restaurantViews = restaurantRepository.getAllBy(pageableRequest);
        return restaurantViews.toList();
    }
}
