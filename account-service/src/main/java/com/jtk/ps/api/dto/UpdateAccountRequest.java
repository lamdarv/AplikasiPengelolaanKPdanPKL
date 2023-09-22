package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {

    @JsonProperty(value = "id_account")
    private String idAccount;
    private String name;

    @JsonProperty(value = "id_role")
    private Integer idRole;
}
