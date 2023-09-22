package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteRequest {
    @JsonProperty(value = "id_account")
    private Integer idAccount;
}
