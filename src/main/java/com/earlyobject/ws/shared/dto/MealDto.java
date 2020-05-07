package com.earlyobject.ws.shared.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MealDto {
    private static final long serialVersionUID = -1797743639002732773L;

    private long id;

    @NotNull(message = "Date is missing")
    private LocalDate date;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description can not be blank")
    @Size(min = 1, max = 100, message = "Description should be between 2 and 100 characters")
    private String description;

    @NotNull(message = "Price is missing")
    @Range(min = 1, max = 100000, message = "Price should be between 1 and 100000")
    private Double price;

    @NotNull(message = "Restaurant ID is missing")
    @Range(min = 1000, message = "Restaurant ID must be greater than 1000")
    private long restaurantId;

    public MealDto() {
    }

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
