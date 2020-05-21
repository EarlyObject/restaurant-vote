package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.service.MealService;
import com.earlyobject.ws.shared.dto.MealDto;
import com.earlyobject.ws.shared.view.MealView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    MealService mealService;

    @ApiOperation(value = "Create Meal Item Web Service End Point",
            notes = "${mealController.create.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MealDto create(@Valid @RequestBody MealDto mealDto) throws CustomServiceException {
        return mealService.create(mealDto);
    }

    //https://www.baeldung.com/spring-data-jpa-projections
    @ApiOperation(value = "Get Meal Item Web Service End Point",
            notes = "${mealController.get.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public MealView get(@PathVariable long id) {
        return mealService.get(id);
    }


    @ApiOperation(value = "Update Meal Item Web Service End Point",
            notes = "${mealController.update.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public MealDto update(@PathVariable long id, @Valid @RequestBody MealDto mealDto) {
        return mealService.update(id, mealDto);
    }


    @ApiOperation(value = "Delete Meal Item Web Service End Point",
            notes = "${mealController.delete.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel delete(@PathVariable long id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        mealService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


    @ApiOperation(value = "Get All Meal Items Web Service End Point",
            notes = "${mealController.getAll.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization",
            value = "${userController.authorizationHeader.description}",
            paramType = "header")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealView> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "limit", defaultValue = "25") int limit) {

        return mealService.getAll(page, limit);
    }
}
