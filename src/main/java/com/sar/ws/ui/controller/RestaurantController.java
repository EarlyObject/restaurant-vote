package com.sar.ws.ui.controller;

import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.ui.model.request.RestaurantDetailsRequestModel;
import com.sar.ws.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurants") //http://localhost:8080/restaurant-vote/restaurants
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantRest create(@RequestBody RestaurantDetailsRequestModel restaurantModel) throws Exception {
        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        RestaurantDto createdRestaurant = restaurantService.create(restaurantDto);
        BeanUtils.copyProperties(createdRestaurant, returnValue);

        return returnValue;
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantRest get(@PathVariable Long id) throws Exception {
        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto restaurantDto = restaurantService.getById(id);
        BeanUtils.copyProperties(restaurantDto, returnValue);

        return returnValue;
    }

    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantRest update (@PathVariable Long id, @RequestBody RestaurantDetailsRequestModel restaurantModel) {
        RestaurantRest returnValue = new RestaurantRest();

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        RestaurantDto updatedRestaurant = restaurantService.update(id, restaurantDto);
        BeanUtils.copyProperties(updatedRestaurant, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperatioStatusModel delete(@PathVariable Long id) {
        OperatioStatusModel returnValue = new OperatioStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        restaurantService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantRest> getRestaurants(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<RestaurantRest> returnValue = new ArrayList<>();

        if (page > 0) {
            page = page - 1;
        }

        List<RestaurantDto> restaurantDtos = restaurantService.getRestaurants(page, limit);
        for (RestaurantDto restaurantDto : restaurantDtos) {
            RestaurantRest restaurantRest = new RestaurantRest();
            BeanUtils.copyProperties(restaurantDto, restaurantRest);
            returnValue.add(restaurantRest);
        }

        return returnValue;
    }
}
