package com.rithiro.personaltracking.modules.eventGroup.models.responses;

import com.rithiro.personaltracking.models.bases.BaseRef;

import lombok.Data;

@Data
public class EventGroupResponse {
    private Integer id;
    private String name;
    private String color;
    private String colorCode;
    private BaseRef created;
    private BaseRef modified;
}
