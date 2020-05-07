package com.sar.ws.ui.controller;

import com.sar.ws.service.MealService;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.JPAProjection;
import com.sar.ws.shared.view.MealView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
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
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/restaurants") //http://localhost:8080/restaurant-vote/restaurants
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
    public <T extends JPAProjection> T get(@PathVariable @Min(1000) long id,
                                           @RequestParam Optional<Boolean> loadAll) throws Exception {
        return restaurantService.getById(id, loadAll.orElse(false));
    }


    @ApiOperation(value = "Get Restaurant and it's Meal Items Filtered Web Service End Point",
            notes = "${restaurantController.getFiltered.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(path = {"/{id}/filter"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealView> getFiltered(@PathVariable @Min(1000) long id,
                                      @RequestParam(name = "start", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate start,
                                      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate end,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "25") int limit) throws Exception {
        if (page > 0) page = page - 1;
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
    public RestaurantDto update(@PathVariable @Min(1000) long id, @RequestBody @Valid RestaurantDto restaurantDto) {
        return restaurantService.update(id, restaurantDto);
    }


    @ApiOperation(value = "Delete Restaurant Web Service End Point",
            notes = "${restaurantController.delete.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel delete(@PathVariable @Min(1000) long id) {
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

        if (page > 0) page = page - 1;
        return restaurantService.getAll(page, limit, loadAll.orElse(false));
    }
}
