package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayloadJwt {
    @JsonProperty("id_prodi")
    private Integer idProdi;

    private String sub;

    @JsonProperty("id_role")
    private Integer idRole;

    @JsonProperty("id")
    private Integer id;
    
    private String name;

}
