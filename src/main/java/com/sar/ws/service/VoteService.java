package com.sar.ws.service;

import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.response.OperationStatusModel;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {

    OperationStatusModel create(String userId, long restaurantId, LocalDateTime postTime);

    List<VoteView> getVotes (String userId, Pageable pageable);
}