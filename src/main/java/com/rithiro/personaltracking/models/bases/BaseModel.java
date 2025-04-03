package com.rithiro.personaltracking.models.bases;

import java.sql.Date;

import lombok.Data;

@Data
public class BaseModel {
    private Date createdAt;
    private Integer createdBy;
    private Date modifiedAt;
    private Integer modifiedBy;
}
