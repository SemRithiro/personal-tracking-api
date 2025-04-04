package com.rithiro.personaltracking.modules.event.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rithiro.personaltracking.modules.event.mappers.EventMapper;
import com.rithiro.personaltracking.modules.event.models.databases.SubscribedEvent;
import com.rithiro.personaltracking.modules.event.models.responses.SubscribedEventResponse;
import com.rithiro.personaltracking.modules.event.services.EventService;

@Service
@Primary
public class EventServiceImpl implements EventService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventMapper eventMapper;

    @Override
    public List<SubscribedEventResponse> getSubscribedEvents() {
        List<SubscribedEvent> subscribedEvents = eventMapper.getSubscribedEvents(1);
        return subscribedEvents.stream().map(event -> objectMapper.convertValue(event, SubscribedEventResponse.class))
                .toList();
    }

}
