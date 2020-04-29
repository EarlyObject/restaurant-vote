package com.sar.ws.service.impl;

import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.JPAProjection;
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
    public JPAProjection getById(Long id, boolean loadAll) {
        Optional<? extends JPAProjection> returnValue;
        if (loadAll) {
            returnValue = restaurantRepository.getById(id, getCurrentDate());
        } else {
            returnValue = restaurantRepository.getAdminRestaurantViewById(id);
        }
        if (returnValue.isPresent()) {
            return returnValue.get();
        } else {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }
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
    public List<? extends JPAProjection> getAll(int page, int limit, boolean loadAll) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<? extends JPAProjection> restaurantViews;
        if (loadAll) {
            restaurantViews = restaurantRepository.getAllWithMealsAndVotes(getCurrentDate(),
                    pageableRequest);
        } else {
            restaurantViews = restaurantRepository.getAllBy();
        }

        return restaurantViews;
    }
}
