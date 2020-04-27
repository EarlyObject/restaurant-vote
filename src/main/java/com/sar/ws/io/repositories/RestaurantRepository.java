package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Restaurant;
import com.sar.ws.shared.view.RestaurantView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {


    @Query("select r from Restaurant r join fetch r.meals")
    List<Restaurant> findAllWithMeals();

    @Transactional(readOnly = true)
    Optional<RestaurantView> getById(long id);

    @Transactional(readOnly = true)
    Page<RestaurantView> getAllBy(Pageable pageable);
}
