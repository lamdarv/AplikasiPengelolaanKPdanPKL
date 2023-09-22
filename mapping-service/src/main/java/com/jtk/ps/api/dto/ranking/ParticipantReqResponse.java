package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantReqResponse {
    @JsonProperty("id_participant")
    private Integer idParticipant;

    @JsonProperty("id_domicile")
    private Integer idDomicile;

    @JsonProperty("jobscope")
    private List<JobscopeResponse> jobscope;

    @JsonProperty("competence")
    private List<CompetenceParticipant> competence;
}
