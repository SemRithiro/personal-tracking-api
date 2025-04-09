package com.rithiro.personaltracking.modules.eventGroup.models.databases;

import com.rithiro.personaltracking.models.bases.BaseRef;

import lombok.Data;

@Data
public class EventGroup {
    private Integer id;
    private String name;
    private String color;
    private String colorCode;
    private BaseRef created;
    private BaseRef modified;
}
