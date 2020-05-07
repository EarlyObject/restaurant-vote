package com.earlyobject.ws.service.impl;

import com.earlyobject.ws.entity.Meal;
import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.shared.dto.MealDto;
import com.earlyobject.ws.shared.view.MealView;
import com.earlyobject.ws.ui.model.response.ErrorMessages;
import com.earlyobject.ws.io.repositories.MealRepository;
import com.earlyobject.ws.io.repositories.RestaurantRepository;
import com.earlyobject.ws.service.MealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
            throw new CustomServiceException("Restaurant with ID: " + restaurantId + " not found");
        }

        Meal meal = new Meal();
        BeanUtils.copyProperties(mealDto, meal);

        Meal savedMeal = mealRepository.save(meal);
        BeanUtils.copyProperties(savedMeal, mealDto);
        return mealDto;
    }

    @Override
    public MealView get(long id) {
        Optional<MealView> mealViewOptional = mealRepository.getById(id);
        if (mealViewOptional.isEmpty()) {
            throw new CustomServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return mealViewOptional.get();
    }

    @Override
    @CacheEvict(value = "meals", key = "#id")
    public MealDto update(long id, MealDto mealDto) {
        long restaurantId = mealDto.getRestaurantId();
        Optional<Meal> mealOptional = mealRepository.findById(id);
        if (mealOptional.isEmpty()) {
            throw new CustomServiceException("Meal with ID: " + id + " not found");
        }

        //заменить на Optional??
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new CustomServiceException("Restaurant with ID: " + restaurantId + " not found");
        }

        Meal meal = mealOptional.get();
        BeanUtils.copyProperties(mealDto, meal, "id");

        Meal updatedMeal = mealRepository.save(meal);
        BeanUtils.copyProperties(updatedMeal, mealDto);
        return mealDto;
    }

    @Override
    @CacheEvict(value = "meals", key = "#id")
    public void delete(long id) {
        Optional<Meal> mealOptional = mealRepository.findById(id);

        if (mealOptional.isEmpty()) {
            throw new CustomServiceException("Meal with ID: " + id + " not found");
        }
        mealRepository.delete(mealOptional.get());
    }

    @Override
    @Cacheable(value = "meals")
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
