package com.earlyobject.ws.service.impl.unitTests;

import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.earlyobject.ws.RestaurantTestData.RESTAURANT_ID;
import static com.earlyobject.ws.UserTestData.USER_ID;
import static com.earlyobject.ws.UserTestData.getUserEntity;
import static com.earlyobject.ws.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class VoteServiceImplTest extends AbstractServiceTest{

    @Test
    void create() {
        UserEntity userEntity = getUserEntity();
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userEntity));
        when(voteRepository.findByUserIdAndDate(anyLong(), any())).thenReturn(Optional.of(VOTE));
        when(voteRepository.save(any())).thenReturn(VOTE);
        OperationStatusModel returnValue = voteService.create(USER_ID, RESTAURANT_ID, DATE_TIME);
        assertNotNull(RequestOperationName.VOTE.name(), returnValue.getOperationName());
        assertEquals(RequestOperationStatus.SUCCESS.name(), returnValue.getOperationResult());
    }

    @Test
    void getVotes() {
        UserEntity userEntity = getUserEntity();
        List<VoteView> voteViews = new ArrayList<>(Arrays.asList(getVoteView(), getVoteView()));
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userEntity));
        when(voteRepository.getAllByUserId(anyLong(), any())).thenReturn(voteViews);
        List<VoteView> returnValue = voteService.getAll("", 1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
    }
}