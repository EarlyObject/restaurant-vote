package com.earlyobject.ws.io.repositories;

import com.earlyobject.ws.entity.Restaurant;
import com.earlyobject.ws.shared.view.AdminRestaurantView;
import com.earlyobject.ws.shared.view.RestaurantView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.meals m WHERE r.id=?1 AND m.date=?2")
    Optional<RestaurantView> getById(long id, LocalDate date);

    Optional<AdminRestaurantView> getAdminRestaurantViewById(long id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.meals m WHERE m.date=?1")
    List<RestaurantView> getAllWithMealsAndVotes(LocalDate date, Pageable pageable);

    List<AdminRestaurantView> getAllBy();
}
