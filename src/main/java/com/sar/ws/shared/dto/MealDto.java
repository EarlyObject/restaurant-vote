package com.sar.ws.shared.dto;

import com.sar.ws.io.entity.Restaurant;

import java.time.LocalDate;

public class MealDto {
    private static final long serialVersionUID = 8367849004199865235L;
    private long id;
    private LocalDate date;
    private String description;
    private Double price;
    private long restaurantId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
