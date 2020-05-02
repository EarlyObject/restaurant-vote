package com.sar.ws.ui.controller;

import com.sar.ws.service.MealService;
import com.sar.ws.service.RestaurantService;
import com.sar.ws.shared.dto.RestaurantDto;
import com.sar.ws.shared.view.JPAProjection;
import com.sar.ws.shared.view.MealView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants") //http://localhost:8080/restaurant-vote/restaurants
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MealService mealService;

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto create(@RequestBody RestaurantDto restaurantModel) throws Exception {

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        return restaurantService.create(restaurantDto);
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public <T extends JPAProjection> T get(@PathVariable Long id,
                                           @RequestParam Optional<Boolean> loadAll) throws Exception {
        return restaurantService.getById(id, loadAll.orElse(false));
    }

    @GetMapping(path = {"/{id}/filter"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealView> getFiltered(@PathVariable Long id,
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

    @Secured("ROLE_ADMIN")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDto update(@PathVariable Long id, @RequestBody RestaurantDto restaurantModel) {

        RestaurantDto restaurantDto = new RestaurantDto();
        BeanUtils.copyProperties(restaurantModel, restaurantDto);

        return restaurantService.update(id, restaurantDto);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel delete(@PathVariable Long id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        restaurantService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    //consult if get and getAll methods access should be allowed to unregistered users
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends JPAProjection> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit,
                                                @RequestParam Optional<Boolean> loadAll) {

        if (page > 0) page = page - 1;

        List<? extends JPAProjection> returnValue = restaurantService.getAll(page, limit,
                loadAll.orElse(false));
        return returnValue;
    }
}
