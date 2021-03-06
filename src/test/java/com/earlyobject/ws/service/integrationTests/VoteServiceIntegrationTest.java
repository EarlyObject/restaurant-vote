package com.earlyobject.ws.service.integrationTests;

import com.earlyobject.ws.entity.Vote;
import com.earlyobject.ws.io.repositories.VoteRepository;
import com.earlyobject.ws.service.VoteService;
import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.earlyobject.ws.RestaurantTestData.RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VoteServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteService voteService;

    @Test
    void create() {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());
        long userID = 1020;
        LocalDateTime dateTime = LocalDateTime.now();
        OperationStatusModel operationStatusModel = voteService.create(
                userID, RESTAURANT_ID, dateTime);
        assertNotNull(operationStatusModel);
        Optional<Vote> byUserIdAndDate = voteRepository.findByUserIdAndDate(1020L, LocalDate.now());
        assertNotNull(byUserIdAndDate);
        Vote vote = byUserIdAndDate.get();
        assertEquals(dateTime, vote.getCreated());
        assertEquals(RESTAURANT_ID, vote.getRestaurantId());
    }

    @Test
    void getAll() {
        List<VoteView> votes = voteService.getAll(1000, 0, 10);
        assertNotNull(votes);
        assertEquals(4, votes.size());

    }
}