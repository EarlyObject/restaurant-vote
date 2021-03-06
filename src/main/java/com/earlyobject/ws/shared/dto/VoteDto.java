package com.earlyobject.ws.shared.dto;

import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.entity.UserEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VoteDto implements Serializable {
    private static final long serialVersionUID = -7002256217302048633L;

    private long id;
    private LocalDateTime created;
    private UserEntity user;
    private Restaurant restaurant;
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
