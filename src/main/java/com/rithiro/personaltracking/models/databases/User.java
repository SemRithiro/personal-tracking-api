package com.rithiro.personaltracking.models.databases;

import com.rithiro.personaltracking.models.bases.BaseRef;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private BaseRef created;
    private BaseRef modified;
}
