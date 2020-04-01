package com.sar.ws.service.impl;

import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Meal;
import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.MealDto;
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
    public RestaurantDto getById(Long id) {
        //В этом методе, и в getAll достается ресторан вместе с едой которая содержит
        //рестораны (каскад), надо разобраться
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);


        //Здесь надо сделать проверку на обязательные поля либо через Validation либо через перебор поле
        if (!restaurantOptional.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + id + " not found");
        }
        Restaurant restaurant = restaurantOptional.get();

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurant, restaurantDto);

  /*      ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);


        modelMapper.typeMap(Meal.class, MealDto.class).addMappings(mapper -> {
            mapper.map(src->src.getRestaurant().getId(),
                    MealDto::setRestaurantId);

           *//* mapper.map(src -> src.getBillingAddress().getCity(),
                    Destination::setBillingCity);*//*
        });
        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);*/


       /* restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setPhoneNumber(restaurant.getPhoneNumber());

        List<MealDto> mealDtos = new ArrayList<>();
        for (Meal meal : restaurant.getMeals()) {
            MealDto mealDto = new MealDto();
            mealDto.setId(meal.getId());
            mealDto.setRestaurantId(restaurant.getId());
            mealDto.setDate(meal.getDate());
            mealDto.setDescription(meal.getDescription());
            mealDto.setPrice(meal.getPrice());

            mealDtos.add(mealDto);
        }
        restaurantDto.setMeals(mealDtos);*/

        return restaurantDto;
    }

    @Override
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        RestaurantDto returnValue = new RestaurantDto();

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

     /*  restaurantRepository.findById(id)
               .filter(restaurant->restaurant.getMeals()
               .forEach(meal-> meal.getDate().isEqual(LocalDate.of(2020, 4, 2))));

*/
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
