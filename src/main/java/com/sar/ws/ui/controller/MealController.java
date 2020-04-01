package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.service.MealService;
import com.sar.ws.shared.dto.MealDto;
import com.sar.ws.ui.model.request.MealDetailsRequestModel;
import com.sar.ws.ui.model.response.ErrorMessages;
import com.sar.ws.ui.model.response.OperatioStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    MealService mealService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MealDto create(@RequestBody MealDetailsRequestModel mealDetails) throws UserServiceException {

        //проверить, что нужно добавить/убрать
        if (mealDetails.getDescription().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        MealDto mealDto = new MealDto();
        BeanUtils.copyProperties(mealDetails, mealDto);

        return mealService.create(mealDto);
    }

    //проверить во всех котролеерах на long/Long
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public MealDto get(@PathVariable long id) {
        return mealService.get(id);
    }

    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public MealDto update(@PathVariable long id, @RequestBody MealDetailsRequestModel mealDetails) {
        MealDto mealDto = new MealDto();
        BeanUtils.copyProperties(mealDetails, mealDto);

        return mealService.update(id, mealDto);
    }

    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperatioStatusModel delete(@PathVariable long id) {
        OperatioStatusModel returnValue = new OperatioStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        mealService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealDto> getMeals(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) {
            page = page - 1;
        }

        return mealService.getMeals(page, limit);
    }
}
