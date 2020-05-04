package com.sar.ws.io.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "meals",
        indexes = {@Index(name = "idx_id", columnList = "id"),
                @Index(name = "idx_restaurantId", columnList = "restaurant_id"),
                @Index(name = "idx_date", columnList = "date"),
                @Index(name = "idx_restaurantId_date", columnList = "restaurant_id, date")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "description", "restaurant_id"})})
public class Meal extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Column(name = "restaurant_id")
    private long restaurantId;

    public Meal() {
    }

    public Meal(long id, LocalDate date, String description, Double price, long restaurantId) {
        super(id);
        this.date = date;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                ", restaurantId=" + restaurantId +
                ", id=" + id +
                '}';
    }
}
