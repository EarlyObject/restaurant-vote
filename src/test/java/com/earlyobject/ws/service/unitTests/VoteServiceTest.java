package com.earlyobject.ws.service.unitTests;

import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.earlyobject.ws.RestaurantTestData.RESTAURANT_ID;
import static com.earlyobject.ws.UserTestData.ID;
import static com.earlyobject.ws.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class VoteServiceTest extends AbstractServiceTest{

    @Test
    void create() {
        when(voteRepository.save(any())).thenReturn(VOTE);
        OperationStatusModel returnValue = voteService.create(ID, RESTAURANT_ID, DATE_TIME);
        assertNotNull(RequestOperationName.VOTE.name(), returnValue.getOperationName());
        assertEquals(RequestOperationStatus.SUCCESS.name(), returnValue.getOperationResult());
    }

    @Test
    void getVotes() {
        List<VoteView> voteViews = new ArrayList<>(Arrays.asList(getVoteView(), getVoteView()));
        when(voteRepository.getAllByUserId(anyLong(), any())).thenReturn(voteViews);
        List<VoteView> returnValue = voteService.getAll(ID, 1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
    }
}