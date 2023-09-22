package com.jtk.ps.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetenceAndType {
    private int id;

    @JsonProperty("id_competence")
    private Integer idCompetence;

    @JsonProperty("competence_type")
    private Integer competenceType;
    
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
