package com.sar.ws.io.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"})})
public class Restaurant extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date DESC")
    private List<Meal> meals;

    //check lazyCollection
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created DESC")
    private Set<Vote> votes;

   /* @Formula("select count(*) from votes v where v.restaurant_id = id")
    private int votesCount;*/

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.meals = restaurant.getMeals();
        this.votes = restaurant.getVotes();
//        this.votesCount = restaurant.getVotesCount();
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

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    /*public Integer getVotesCount() {
        return votes.size();
    }*/

//    public void setVotesCount(int votesCount) {
//        this.votesCount = votesCount;
//    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }
}
