package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCard {
    @JsonProperty("participant_id")
    private Integer participantId;
    
    @JsonProperty("participant_name")
    private String participantName;
    
    @JsonProperty("participant_prodi")
    private Integer participantProdi;
    
    @JsonProperty("num_evaluation")
    private Integer numEvaluation;
    
    @JsonProperty("status_submit")
    private Integer statusSubmit;
}
