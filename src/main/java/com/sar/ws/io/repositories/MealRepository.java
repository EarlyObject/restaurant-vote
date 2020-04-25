package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Meal;
import com.sar.ws.shared.view.MealView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MealRepository extends PagingAndSortingRepository<Meal, Long> {

    @Transactional(readOnly = true)
    Optional<MealView> getById(long id);


    @Transactional(readOnly = true)
    Page<MealView> getAllBy(Pageable pageable);
}
