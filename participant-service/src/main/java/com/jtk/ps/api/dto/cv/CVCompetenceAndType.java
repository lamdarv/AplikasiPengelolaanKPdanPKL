package com.jtk.ps.api.dto.cv;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CVCompetenceAndType {
    private int id;

    @JsonProperty("competence_id")
    private Integer competenceId;

    @JsonProperty("competence_type")
    private Integer competenceType;

    @JsonProperty("knowledge_id")
    private Integer knowledgeId;
}
