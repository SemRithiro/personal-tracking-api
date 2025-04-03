package com.rithiro.personaltracking.models.databases;

import lombok.Data;

@Data
public class Oauth2AccessToken {
    private Integer id;
    private Integer userId;
    private String tokenId;
    private String tokenValue;
    private Boolean revoked;
}
