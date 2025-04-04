package com.rithiro.personaltracking.models.databases;

import lombok.Data;

@Data
public class Auth {
    private Integer id;
    private String authId;
    private String username;
    private String password;
}
