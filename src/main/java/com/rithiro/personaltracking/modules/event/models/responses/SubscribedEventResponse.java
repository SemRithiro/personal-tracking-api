package com.rithiro.personaltracking.modules.event.models.responses;

// import com.rithiro.personaltracking.models.bases.BaseRef;

import lombok.Data;

@Data
public class SubscribedEventResponse {
    private Integer id;
    private String name;
    private String url;
    private String color;
    private String colorCode;
    private String format = "ics";
    // private BaseRef created;
    // private BaseRef modified;
}
