package com.sar.ws.service.impl;

import com.sar.ws.exceptions.CustomServiceException;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.JPAProjection;
import com.sar.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sar.ws.shared.dto.Utils.getCurrentDate;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDto create(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);

        Restaurant storedRestaurant = restaurantRepository.save(restaurant);
        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(storedRestaurant, returnValue);
        return returnValue;
    }

    @Override
    public  <T extends JPAProjection> T getById(Long id, boolean loadAll) {
        Optional<T> returnValue;
        if (loadAll) {
            returnValue = (Optional<T>) restaurantRepository.getById(id, getCurrentDate());
        } else {
            returnValue = (Optional<T>) restaurantRepository.getAdminRestaurantViewById(id);
        }

        if (returnValue.isEmpty()) {
            throw new CustomServiceException("Restaurant with ID: " + id + " not found or has no meal today");
        }
        return returnValue.get();
    }

    @Override
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new CustomServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        Restaurant restaurant = restaurantOptional.get();
        BeanUtils.copyProperties(restaurantDto, restaurant, "id");
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        BeanUtils.copyProperties(updatedRestaurant, restaurantDto);
        return restaurantDto;
    }

    @Override
    public void delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new CustomServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        restaurantRepository.delete(restaurantOptional.get());
    }

    @Override
    public List<? extends JPAProjection> getAll(int page, int limit, boolean loadAll) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<? extends JPAProjection> restaurantViews;
        if (loadAll) {
            restaurantViews = restaurantRepository.getAllWithMealsAndVotes(getCurrentDate(), pageableRequest);
        } else {
            restaurantViews = restaurantRepository.getAllBy();
        }
        return restaurantViews;
    }
}
