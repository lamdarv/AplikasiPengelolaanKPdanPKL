package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private String name;

    @JsonProperty("id_prodi")
    private Integer idProdi;

    @JsonProperty("id_role")
    private Integer idRole;

    @JsonProperty("username")
    private String username;
}
