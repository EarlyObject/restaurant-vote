package com.sar.ws.io.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable {
    private static final long serialVersionUID = -157747531241672180L;

//    LocalDate today = LocalDateTime.now().toLocalDate();
    static LocalDate today = LocalDate.of(2020, 4, 20);


    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotBlank
    private String phoneNumber;

    //    @OneToMany(mappedBy = "restaurantId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private List<Meal> meals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    @OrderBy("created DESC")
    private List<Vote> votes;

    public Restaurant() {
    }

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

    public List<Meal> getMeals() {
        return meals;
      /*  List<Meal> collect = meals.stream()
                .filter(n -> n.getDate().equals(toda))
                .collect(Collectors.toList());
        return collect;*/
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Integer getVotesCount() {
        return votes.size();
    }

    public List<Meal> getTodayMenu(){
        List<Meal> collect = meals.stream()
                .filter(n -> n.getDate().equals(today))
                .collect(Collectors.toList());
        return collect;
    }
}
