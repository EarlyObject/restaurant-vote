package com.earlyobject.ws.service.impl.integrationTests;

import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.io.repositories.MealRepository;
import com.earlyobject.ws.service.MealService;
import com.earlyobject.ws.shared.dto.MealDto;
import com.earlyobject.ws.shared.view.MealView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.earlyobject.ws.MealTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class MealServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealService mealService;

    @Test
    void create() {
        MealDto mealDto = new MealDto();
        mealDto.setDescription(MEAL1.getDescription());
        mealDto.setDate(MEAL1.getDate());
        mealDto.setPrice(MEAL1.getPrice());
        mealDto.setRestaurantId(1009L);
        MealDto returnValue = mealService.create(mealDto);
        assertNotNull(returnValue);
        assertEquals(MEAL1.getDescription(), returnValue.getDescription());
        assertEquals(MEAL1.getDate(), returnValue.getDate());
        assertEquals(MEAL1.getPrice(), returnValue.getPrice());
        assertEquals(1009, returnValue.getRestaurantId());
    }

    @Test
    void get() {
        MealView byId = mealService.get(MEAL2.getId());
        assertNotNull(byId);
        assertEquals(MEAL2.getDate(), byId.getDate());
        assertEquals(MEAL2.getRestaurantId(), byId.getRestaurantId());
        assertEquals(MEAL2.getDescription(), byId.getDescription());
        assertEquals(MEAL2.getPrice(), byId.getPrice());
    }

    @Test
    void update() {
        MealDto mealDto = new MealDto();
        mealDto.setDescription("NewDescription");
        mealDto.setDate(DATE);
        mealDto.setPrice(400.00);
        mealDto.setRestaurantId(1009);
        MealDto updatedDto = mealService.update(1016L, mealDto);
        assertNotNull(updatedDto);
        assertEquals(DATE, updatedDto.getDate());
        assertEquals(1009, updatedDto.getRestaurantId());
        assertEquals("NewDescription", updatedDto.getDescription());
        assertEquals(400.00, updatedDto.getPrice());
    }

    @Test
    void delete() {
        mealService.delete(1016L);
        assertThrows(CustomServiceException.class, () -> mealService.get(1016L));
    }

    @Test
    void getAll() {
        List<MealView> all = mealService.getAll(0, 10);
        assertNotNull(all);
        assertEquals(10, all.size());
    }

    @Test
    void getFiltered() {
        Pageable pageable = PageRequest.of(0, 10);
        List<MealView> filtered = mealService.getFiltered(1007L, LocalDate.now(), LocalDate.now(), pageable);
        assertNotNull(filtered);
        assertEquals(2, filtered.size());
    }
}