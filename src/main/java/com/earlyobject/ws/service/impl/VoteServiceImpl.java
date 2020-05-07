package com.earlyobject.ws.service.impl;

import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.io.repositories.VoteRepository;
import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.entity.Vote;
import com.earlyobject.ws.io.repositories.UserRepository;
import com.earlyobject.ws.service.VoteService;
import com.earlyobject.ws.ui.model.response.ErrorMessages;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @Override
    public OperationStatusModel create(String userId, long restaurantId, LocalDateTime postTime) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());
        long id;
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new CustomServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } else {
            id = optionalUserEntity.get().getId();
        }
        Optional<Vote> voteOptional = voteRepository.findByUserIdAndDate(id, postTime.toLocalDate());
        Vote vote;
        if (voteOptional.isEmpty()) {
            vote = new Vote(id, restaurantId, postTime);
            voteRepository.save(vote);
        } else {
            if (postTime.toLocalTime().isAfter(LocalTime.of(11, 00, 00))) {
                returnValue.setOperationResult(RequestOperationStatus.DENIED.name());
                return returnValue;
            } else {
                vote = voteOptional.get();
                vote.setCreated(postTime);
                vote.setRestaurantId(restaurantId);
                voteRepository.save(vote);
            }
        }
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @Override
    public List<VoteView> getAll(String userId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        long id;
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new CustomServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } else {
            id = optionalUserEntity.get().getId();
            return voteRepository.getAllByUserId(id, pageable);
        }
    }
}
