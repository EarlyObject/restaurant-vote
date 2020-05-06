package com.sar.ws.service.impl.integrationTests;

import com.sar.ws.io.entity.Vote;
import com.sar.ws.io.repositories.VoteRepository;
import com.sar.ws.service.impl.VoteServiceImpl;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sar.ws.RestaurantTestData.RESTAURANT_ID;
import static com.sar.ws.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VoteServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteServiceImpl voteService;

    @Test
    void create() {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());
        String userID = "tMa4UtLClcoGUmozrDEt";
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
    void getVotes() {
        List<VoteView> votes = voteService.getVotes(USER_ID, 0, 10);
        assertNotNull(votes);
        assertEquals(4, votes.size());

    }
}