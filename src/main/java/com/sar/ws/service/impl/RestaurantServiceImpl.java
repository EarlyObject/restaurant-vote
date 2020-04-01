package com.sar.ws.service.impl;

import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDto create(RestaurantDto restaurantDto) {

      /*  if (restaurantRepository.findById(restaurantDto.getId()) != null) {
            throw new RuntimeException("Record already exists");
        }*/

        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);

        Restaurant storedRestaurant = restaurantRepository.save(restaurant);
        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(storedRestaurant, returnValue);

        return returnValue;
    }

    @Override
    public RestaurantDto getById(Long id) {
        RestaurantDto returnValue = new RestaurantDto();
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (!restaurant.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }

        BeanUtils.copyProperties(restaurant.get(), returnValue);
        return returnValue;
    }

    @Override
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        RestaurantDto returnValue = new RestaurantDto();

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (!restaurantOptional.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }

        Restaurant restaurant = restaurantOptional.get();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());

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
    public List<RestaurantDto> getRestaurants(int page, int limit) {
        List<RestaurantDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageableRequest);
        List<Restaurant> restaurants = restaurantPage.getContent();

        for (Restaurant restaurant : restaurants) {
            RestaurantDto restaurantDto = new RestaurantDto();
            BeanUtils.copyProperties(restaurant, restaurantDto);
            returnValue.add(restaurantDto);
        }

        return returnValue;
    }
}
