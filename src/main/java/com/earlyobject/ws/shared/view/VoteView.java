package com.earlyobject.ws.shared.view;

import java.time.LocalDateTime;

public interface VoteView {

    long getId();

    LocalDateTime getCreated();

    long getRestaurantId();

    long getUserId();

    void setId(long id);

    void setCreated(LocalDateTime dateTime);

    void setRestaurantId(long restaurantId);

    void setUserId(long userId);
}
