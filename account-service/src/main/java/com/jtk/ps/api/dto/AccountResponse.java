package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.EProdi;
import com.jtk.ps.api.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    @JsonProperty(value = "id_account")
    private Integer idAccount;
    private String name;
    private String username;

    @JsonProperty(value = "id_role")
    private Integer idRole;

    @JsonProperty(value = "id_prodi")
    private Integer idProdi;

    public AccountResponse(Integer idAccount, String name, String username, ERole role, EProdi prodi){
        this.idAccount = idAccount;
        this.name = name;
        this.username = username;
        this.idRole = role.id;
        this.idProdi = prodi.id;
    }
}
