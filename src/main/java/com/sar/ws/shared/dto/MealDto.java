package com.sar.ws.shared.dto;

import java.time.LocalDate;

public class MealDto {
    private static final long serialVersionUID = -1797743639002732773L;
    private long id;
    private LocalDate date;
    private String description;
    private Double price;

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
}
