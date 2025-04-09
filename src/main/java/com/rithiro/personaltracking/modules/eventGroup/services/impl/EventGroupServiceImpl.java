package com.rithiro.personaltracking.modules.eventGroup.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rithiro.personaltracking.modules.eventGroup.mappers.EventGroupMapper;
import com.rithiro.personaltracking.modules.eventGroup.models.databases.EventGroup;
import com.rithiro.personaltracking.modules.eventGroup.models.responses.EventGroupResponse;
import com.rithiro.personaltracking.modules.eventGroup.services.EventGroupService;

@Service
@Primary
public class EventGroupServiceImpl implements EventGroupService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventGroupMapper eventGroupMapper;

    @Override
    public List<EventGroupResponse> getList(Integer id) {
        List<EventGroup> eventGroups = eventGroupMapper.getList(id);
        return eventGroups.stream().map(eventGroup -> objectMapper.convertValue(eventGroup, EventGroupResponse.class))
                .toList();
    }

}
