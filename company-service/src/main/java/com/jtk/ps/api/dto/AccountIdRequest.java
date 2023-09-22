package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountIdRequest {
    @JsonProperty(value = "id_account")
    private List<Integer> idAccount;
}
