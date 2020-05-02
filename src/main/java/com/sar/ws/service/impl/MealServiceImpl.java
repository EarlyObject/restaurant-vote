package com.sar.ws.service.impl;

import com.sar.ws.exceptions.MealServiceException;
import com.sar.ws.exceptions.RestaurantServiceException;
import com.sar.ws.io.entity.Meal;
import com.sar.ws.io.repositories.MealRepository;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.service.MealService;
import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.shared.view.MealView;
import com.sar.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        long restaurantId = mealDto.getRestaurantId();
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantServiceException("Restaurant with ID: " + restaurantId + " not found");
        }

        Meal meal = new Meal();
        BeanUtils.copyProperties(mealDto, meal);

        Meal savedMeal = mealRepository.save(meal);
        BeanUtils.copyProperties(savedMeal, mealDto);
        return mealDto;
    }

    @Override
    public MealView getById(long id) {
        Optional<MealView> mealViewOptional = mealRepository.getById(id);
        if (mealViewOptional.isEmpty()) {
            throw new MealServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return mealViewOptional.get();
    }

    @Override
    public MealDto update(long id, MealDto mealDto) {
        long restaurantId = mealDto.getRestaurantId();
        Optional<Meal> mealOptional = mealRepository.findById(id);
        if (mealOptional.isEmpty()) {
            throw new MealServiceException("Meal with ID: " + id + " not found");
        }
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantServiceException("Restaurant with ID: " + restaurantId + " not found");
        }

        Meal meal = mealOptional.get();
        BeanUtils.copyProperties(mealDto, meal, "id");

        Meal updatedMeal = mealRepository.save(meal);
        BeanUtils.copyProperties(updatedMeal, mealDto);
        return mealDto;
    }

    @Override
    public void delete(long id) {
        Optional<Meal> mealOptional = mealRepository.findById(id);

        if (mealOptional.isEmpty()) {
            throw new MealServiceException("Meal with ID: " + id + " not found");
        }
        mealRepository.delete(mealOptional.get());
    }

    @Override
    public List<MealView> getAll(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        return mealRepository.getAllBy(pageableRequest);
    }

    @Override
    public List<MealView> getFiltered(long restaurantId, @Nullable LocalDate start, @Nullable LocalDate end, Pageable pageable) {
        List<MealView> returnValue;

        if (start == null && end == null) {
            returnValue = mealRepository.getByRestaurantId(restaurantId, pageable);
        } else if (start == null) {
            returnValue = mealRepository.getByRestaurantIdAndDateIsBefore(restaurantId, end, pageable);
        } else if (end == null) {
            returnValue = mealRepository.getByRestaurantIdAndDateIsAfter(restaurantId, start, pageable);
        } else {
            returnValue = mealRepository.getByRestaurantIdAndDateIsBetween(restaurantId, start, end, pageable);
        }
        return returnValue;
    }
}
