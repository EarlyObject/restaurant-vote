package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.service.MealService;
import com.earlyobject.ws.service.RestaurantService;
import com.earlyobject.ws.shared.dto.RestaurantDto;
import com.earlyobject.ws.shared.view.JPAProjection;
import com.earlyobject.ws.shared.view.MealView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MealService mealService;

    @ApiOperation(value = "Create Restaurant Web Service End Point",
            notes = "${restaurantController.create.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto create(@RequestBody @Valid RestaurantDto restaurantDto) {
        return restaurantService.create(restaurantDto);
    }


    @ApiOperation(value = "Get Restaurant Web Service End Point",
            notes = "${restaurantController.get.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public <T extends JPAProjection> T get(@PathVariable long id,
                                           @RequestParam Optional<Boolean> loadAll) {

        if (loadAll.isEmpty() || !loadAll.get()) {
            return restaurantService.get(id);
        } else {
            return restaurantService.getWithMeal(id);
        }
    }


    @ApiOperation(value = "Get Restaurant and it's Meal Items Filtered Web Service End Point",
            notes = "${restaurantController.getFiltered.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(path = {"/{id}/filter"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealView> getFiltered(@PathVariable long id,
                                      @RequestParam(name = "start", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate start,
                                      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate end,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "25") int limit) {

        Pageable pageable = PageRequest.of(page, limit);
        return mealService.getFiltered(id, start, end, pageable);
    }


    @ApiOperation(value = "Update Restaurant Web Service End Point",
            notes = "${restaurantController.update.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @Secured("ROLE_ADMIN")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto update(@PathVariable long id, @RequestBody @Valid RestaurantDto restaurantDto) {
        return restaurantService.update(id, restaurantDto);
    }


    @ApiOperation(value = "Delete Restaurant Web Service End Point",
            notes = "${restaurantController.delete.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel delete(@PathVariable long id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        restaurantService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


    @ApiOperation(value = "Get All Restaurants Web Service End Point",
            notes = "${restaurantController.getAll.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends JPAProjection> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit,
                                                @RequestParam Optional<Boolean> loadAll) {

        if (loadAll.isEmpty() || !loadAll.get()) {
            return restaurantService.getAll(page, limit);
        } else {
            return restaurantService.getAllWithMeals(page, limit);
        }
    }
}
