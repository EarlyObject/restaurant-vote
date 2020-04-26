package com.sar.ws.shared.view;

import java.time.LocalDateTime;

public interface VoteView {

    long getId();

    LocalDateTime getCreated();

    long getRestaurantId();

    long getUserId();
}
