package com.sar.ws.service.impl;

import com.sar.ws.exceptions.CustomServiceException;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.io.repositories.VoteRepository;
import com.sar.ws.service.VoteService;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.response.ErrorMessages;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
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

    //разобраться с id у Entities
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
    public List<VoteView> getVotes(String userId, int page, int limit) {
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
