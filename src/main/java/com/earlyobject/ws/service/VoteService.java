package com.earlyobject.ws.service;

import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {

    OperationStatusModel create(String userId, long restaurantId, LocalDateTime postTime);

    List<VoteView> getAll(String userId, int page, int limit);
}
