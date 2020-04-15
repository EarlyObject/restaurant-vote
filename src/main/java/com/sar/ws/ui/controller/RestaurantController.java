package com.sar.ws.ui.controller;

import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.ui.model.request.RestaurantDetailsRequestModel;
import com.sar.ws.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurants") //http://localhost:8080/restaurant-vote/restaurants
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Secured("ROLE_ADMIN")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto create(@RequestBody RestaurantDetailsRequestModel restaurantModel) throws Exception {

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        return restaurantService.create(restaurantDto);
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto get(@PathVariable Long id) throws Exception {

        return restaurantService.getById(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto update (@PathVariable Long id, @RequestBody RestaurantDetailsRequestModel restaurantModel) {

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        return restaurantService.update(id, restaurantDto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperatioStatusModel delete(@PathVariable Long id) {
        OperatioStatusModel returnValue = new OperatioStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        restaurantService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    //consult if get and getAll methods access should be allowed to unregistered users
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantDto> getRestaurants(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) {
            page = page - 1;
        }

        return restaurantService.getRestaurants(page, limit);
    }
}
