package com.sar.ws.service.impl;

import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.sar.ws.RestaurantTestData.RESTAURANT_ID;
import static com.sar.ws.UserTestData.USER_ID;
import static com.sar.ws.VoteTestData.DATE_TIME;
import static com.sar.ws.VoteTestData.VOTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class VoteServiceImplTest extends AbstractServiceTest{

    @Test
    void create() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1000L);
        List<Vote> voteList = new ArrayList<>();
        userEntity.setVotes(voteList);
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(voteRepository.findByUserIdAndDate(anyLong(), any())).thenReturn(VOTE);
        when(voteRepository.save(any())).thenReturn(VOTE);
        OperationStatusModel returnValue = voteService.create(USER_ID, RESTAURANT_ID, DATE_TIME);
        assertNotNull(RequestOperationName.VOTE.name(), returnValue.getOperationName());
        assertEquals(RequestOperationStatus.SUCCESS.name(), returnValue.getOperationResult());
    }

    @Test
    void getVotes() {
        Pageable pageable = PageRequest.of(1, 10);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1000L);
        List<Vote> votes = new ArrayList<>();
        List<VoteView> voteViews = new ArrayList<>();
        votes.add(VOTE);
        userEntity.setVotes(votes);
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(voteRepository.getAllByUserId(anyLong(), any())).thenReturn(voteViews);

        List<VoteView> returnValue = voteService.getVotes("", pageable);
        assertNotNull(returnValue);
        assertEquals(0, returnValue.size());
    }
}