package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetenceParticipant {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("id_competence")
    private Integer competenceId;

    @JsonProperty("id_knowledge")
    private Integer knowledgeId;

    @JsonProperty("competence_type")
    private Integer competenceType;
}
