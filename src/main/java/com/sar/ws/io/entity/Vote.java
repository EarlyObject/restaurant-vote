package com.sar.ws.io.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1157392787989018975L;

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "user_id")
    private long userId;

    public Vote() {
    }

    public Vote(long userId, long restaurantId, LocalDateTime created) {
        this.created = created;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = created.toLocalDate();
    }

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

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
