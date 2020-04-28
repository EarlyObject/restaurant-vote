package com.sar.ws.io.repositories;

import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.view.VoteView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    @Transactional(readOnly = true)
    List<VoteView> getAllByUserId(long id, Pageable pageable);

    Vote findByUserIdAndDate(long userId, LocalDate date);
}
