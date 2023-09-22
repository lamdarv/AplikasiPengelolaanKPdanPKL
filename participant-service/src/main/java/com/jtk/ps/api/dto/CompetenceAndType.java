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

    @JsonProperty("id_knowledge")
    private Integer idKnowledge;

    @JsonProperty("competence_type")
    private Integer competenceType;
}
