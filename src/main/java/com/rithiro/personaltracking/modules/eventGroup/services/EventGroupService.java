package com.rithiro.personaltracking.modules.eventGroup.services;

import java.util.List;

import com.rithiro.personaltracking.modules.eventGroup.models.responses.EventGroupResponse;

public interface EventGroupService {
    public List<EventGroupResponse> getList(Integer id);
}
