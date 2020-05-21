package com.earlyobject.ws.service;

import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.exceptions.NotFoundException;
import com.earlyobject.ws.io.repositories.RestaurantRepository;
import com.earlyobject.ws.shared.dto.RestaurantDto;
import com.earlyobject.ws.shared.dto.Utils;
import com.earlyobject.ws.shared.view.JPAProjection;
import com.earlyobject.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @CacheEvict(value = {"restaurants", "restaurantsWithMeal"}, allEntries = true)
    public RestaurantDto create(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto, restaurant);

        Restaurant storedRestaurant = restaurantRepository.save(restaurant);
        RestaurantDto returnValue = new RestaurantDto();
        BeanUtils.copyProperties(storedRestaurant, returnValue);
        return returnValue;
    }

    public <T extends JPAProjection> T get(Long id) {
        Optional<T> returnValue;
            returnValue = (Optional<T>) restaurantRepository.getAdminRestaurantViewById(id);
        if (returnValue.isEmpty()) {
            throw new NotFoundException("Restaurant with ID: " + id + " not found");
        }
        return returnValue.get();
    }

    @Cacheable("restaurantsWithMeal")
    public <T extends JPAProjection> T getWithMeal(Long id) {
        Optional<T> returnValue;
            returnValue = (Optional<T>) restaurantRepository.getById(id, Utils.getCurrentDate());
        if (returnValue.isEmpty()) {
            throw new NotFoundException("Restaurant with ID: " + id + " not found or has no meal today");
        }
        return returnValue.get();
    }

    @Transactional
    @CacheEvict(value = {"restaurants", "restaurantsWithMeal"}, allEntries = true)
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        Restaurant restaurant = restaurantOptional.get();
        BeanUtils.copyProperties(restaurantDto, restaurant, "id");
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        BeanUtils.copyProperties(updatedRestaurant, restaurantDto);
        return restaurantDto;
    }

    @Transactional
    @CacheEvict(value = {"restaurants", "restaurantsWithMeal"}, allEntries = true)
    public void delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        restaurantRepository.delete(restaurantOptional.get());
    }

    @Cacheable("restaurants")
    public List<? extends JPAProjection> getAll(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<? extends JPAProjection> restaurantViews;

        restaurantViews = restaurantRepository.getAllBy(pageableRequest);
        return restaurantViews;
    }

    @Cacheable("restaurantsWithMeal")
    public List<? extends JPAProjection> getAllWithMeals(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<? extends JPAProjection> restaurantViews;

        restaurantViews = restaurantRepository.getAllWithMeals(Utils.getCurrentDate(), pageableRequest);
        return restaurantViews;
    }
}
