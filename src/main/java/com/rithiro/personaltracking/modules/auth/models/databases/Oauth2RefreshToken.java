package com.rithiro.personaltracking.modules.auth.models.databases;

import lombok.Data;

@Data
public class Oauth2RefreshToken {
    private String tokenId;
    private Boolean revoked;
    private Integer usageCount;
}
