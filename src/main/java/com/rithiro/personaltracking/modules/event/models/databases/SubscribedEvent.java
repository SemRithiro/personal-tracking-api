package com.rithiro.personaltracking.modules.event.models.databases;

import com.rithiro.personaltracking.models.bases.BaseRef;

import lombok.Data;

@Data
public class SubscribedEvent {
    private Integer id;
    private String name;
    private String url;
    private String color;
    private String colorCode;
    private BaseRef created;
    private BaseRef modified;
}
