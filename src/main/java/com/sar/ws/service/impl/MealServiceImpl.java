package com.sar.ws.service.impl;

import com.sar.ws.exceptions.MealServiceException;
import com.sar.ws.io.entity.Meal;
import com.sar.ws.io.repositories.MealRepository;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.MealService;
import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.shared.dto.MealView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public MealDto create(MealDto mealDto) {

      /*  if (mealRepository.findById(mealDto.g).findUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Record already exists");
        }*/

        Meal meal = new Meal();
        BeanUtils.copyProperties(mealDto, meal);
      /*  Optional<Restaurant> restaurant = restaurantRepository.findById(mealDto.getRestaurantId());
        if (!restaurant.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + mealDto.getRestaurantId() + " not found");
        }*/
//        meal.setRestaurant(restaurant.get());

        Meal savedMeal = mealRepository.save(meal);
        MealDto returnValue = new MealDto();
        BeanUtils.copyProperties(savedMeal, returnValue);
//        returnValue.setRestaurantId(savedMeal.getRestaurant().getId());
        return returnValue;
    }

    @Override
    public MealView getById(long id) {
        return mealRepository.getById(id);
    }

    @Override
    public MealDto update(long id, MealDto mealDto) {
        MealDto returnValue = new MealDto();
        Optional<Meal> mealOptional = mealRepository.findById(id);
        if (!mealOptional.isPresent()) {
            throw new MealServiceException("Meal with ID: " + id + " not found");
        }
        Meal meal = mealOptional.get();
        meal.setDate(mealDto.getDate());
        meal.setDescription(mealDto.getDescription());
        meal.setPrice(mealDto.getPrice());

     /*   Optional<Restaurant> restaurant = restaurantRepository.findById(mealDto.getRestaurantId());
        if (!restaurant.isPresent()) {
            throw new RestaurantServiceException("Restaurant with ID: " + mealDto.getRestaurantId() + " not found");
        }*/
//        meal.setRestaurant(restaurant.get());

        Meal updatedMeal = mealRepository.save(meal);
        BeanUtils.copyProperties(updatedMeal, returnValue);
//        returnValue.setRestaurantId(updatedMeal.getRestaurant().getId());

        return returnValue;
    }

    @Override
    public void delete(long id) {
        Optional<Meal> mealOptional = mealRepository.findById(id);

        if (!mealOptional.isPresent()) {
            throw new MealServiceException("Meal with ID: " + id + " not found");
        }
        mealRepository.delete(mealOptional.get());
    }

    @Override
    public List<MealView> getMeals(int page, int limit) {

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<MealView> mealPage = mealRepository.getAllBy(pageableRequest);

        return mealPage.getContent();
    }


}
