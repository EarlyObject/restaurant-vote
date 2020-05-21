package com.earlyobject.ws.service;

import com.earlyobject.ws.entity.Vote;
import com.earlyobject.ws.exceptions.DuplicateEntryException;
import com.earlyobject.ws.exceptions.IllegalRequestException;
import com.earlyobject.ws.exceptions.LateVoteException;
import com.earlyobject.ws.io.repositories.UserRepository;
import com.earlyobject.ws.io.repositories.VoteRepository;
import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.response.ErrorMessages;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @Transactional
    public OperationStatusModel create(long userId, long restaurantId, LocalDateTime postTime) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());

        Optional<Vote> voteOptional = voteRepository.findByUserIdAndDate(userId, postTime.toLocalDate());
        Vote vote;
        if (voteOptional.isEmpty()) {
            vote = new Vote(userId, restaurantId, postTime);
            voteRepository.save(vote);
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            return returnValue;
        } else {
            throw new DuplicateEntryException(ErrorMessages.DUPLICATE_VOTE.getErrorMessage());
        }
    }

    public OperationStatusModel update(long userId, long restaurantId, LocalDateTime postTime) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());

        Optional<Vote> voteOptional = voteRepository.findByUserIdAndDate(userId, postTime.toLocalDate());
        Vote vote;
        if (voteOptional.isEmpty()) {
            throw new IllegalRequestException(ErrorMessages.NO_VOTE.getErrorMessage());
        } else {
            if (postTime.toLocalTime().isAfter(LocalTime.of(11, 0, 0))) {
                throw new LateVoteException(ErrorMessages.LATE_VOTE.getErrorMessage());
            } else {
                vote = voteOptional.get();
                vote.setCreated(postTime);
                vote.setRestaurantId(restaurantId);
                voteRepository.save(vote);
                returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            }
            return returnValue;
        }
    }

    @Transactional
    public List<VoteView> getAll(long userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return voteRepository.getAllByUserId(userId, pageable);
    }
}
