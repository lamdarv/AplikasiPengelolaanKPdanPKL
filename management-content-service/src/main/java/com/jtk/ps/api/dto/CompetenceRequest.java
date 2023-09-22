package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetenceRequest {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("id_competencetype")
    private Integer idCompetenceType;
    
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
