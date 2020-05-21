package com.earlyobject.ws.io.repositories;

import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.shared.view.UserView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    @Transactional(readOnly = true)
    Optional<UserEntity> findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<UserEntity> findByUserId(String userId);

    @Transactional(readOnly = true)
    Optional<UserView> getByUserId(String userId);

    @Transactional(readOnly = true)
    List<UserView> getAllBy(Pageable pageable);
}
