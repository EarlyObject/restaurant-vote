package com.sar.ws.shared.dto;

import com.sar.ws.io.entity.Meal;

import java.util.List;

public class RestaurantDto {
    private static final long serialVersionUID = 7024178261305933055L;
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<MealDto> meals;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
}
