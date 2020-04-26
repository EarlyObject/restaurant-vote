package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.view.VoteView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    @Transactional(readOnly = true)
    VoteView getById(long id);

    @Transactional(readOnly = true)
    List<VoteView> getAllByUserId(long id, Pageable pageable);

    @Transactional(readOnly = true)
    VoteView getByUserIdAndCreated(long userId, LocalDateTime created);

    @Transactional(readOnly = true)
    Optional<VoteView> getByUserIdAndDate(long userId, LocalDate date);

    Vote findByUserIdAndDate(long userId, LocalDate date);
}
