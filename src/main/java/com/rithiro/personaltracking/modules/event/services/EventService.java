package com.rithiro.personaltracking.modules.event.services;

import java.util.List;

import com.rithiro.personaltracking.modules.event.models.responses.SubscribedEventResponse;

public interface EventService {
    public List<SubscribedEventResponse> getSubscribedEvents();
}
