package com.sar.ws.service.impl;

import com.sar.ws.io.entity.Vote;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.io.repositories.VoteRepository;
import com.sar.ws.service.VoteService;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
        long id = userRepository.findByUserId(userId).getId();
        Vote voteOfDay = voteRepository.findByUserIdAndDate(id, postTime.toLocalDate());
        if (voteOfDay == null) {
            Vote vote = new Vote(id, restaurantId, postTime);
            voteRepository.save(vote);
        } else {
            if (postTime.toLocalTime().isAfter(LocalTime.of(11, 00, 00))) {
                returnValue.setOperationResult(RequestOperationStatus.DENIED.name());
                return returnValue;
            } else {
                voteOfDay.setCreated(postTime);
                voteOfDay.setRestaurantId(restaurantId);
                voteRepository.save(voteOfDay);
            }
        }
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @Override
    public List<VoteView> getVotes(String userId, Pageable pageable) {

        long id = userRepository.findByUserId(userId).getId();
        return voteRepository.getAllByUserId(id, pageable);
    }
}
