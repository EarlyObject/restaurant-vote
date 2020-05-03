package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.shared.view.UserView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(String userId);

    @Transactional(readOnly = true)
    Optional<UserView> getByUserId(String userId);

    List<UserView> getAllBy(Pageable pageable);
}
