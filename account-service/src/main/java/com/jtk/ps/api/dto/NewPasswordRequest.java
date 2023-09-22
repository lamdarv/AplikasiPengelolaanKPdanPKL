package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewPasswordRequest {

    @JsonProperty(value = "old_password")
    private String oldPassword;

    @JsonProperty(value = "new_password")
    private String newPassword;

    @JsonProperty(value = "confirm_new_password")
    private String confirmNewPassword;
}
