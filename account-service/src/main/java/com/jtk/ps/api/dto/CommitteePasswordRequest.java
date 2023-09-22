package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommitteePasswordRequest {

    @JsonProperty(value = "id_account")
    private Integer idAccount;

    @JsonProperty(value = "new_password")
    private String newPassword;

    @JsonProperty(value = "confirm_new_password")
    private String confirmNewPassword;
}
