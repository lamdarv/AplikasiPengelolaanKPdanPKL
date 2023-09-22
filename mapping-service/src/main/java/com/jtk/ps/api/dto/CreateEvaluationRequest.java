package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEvaluationRequest {
    @JsonProperty("company_id")
    private Integer idCompany;
    
    @JsonProperty("participant_id")
    private Integer idParticipant;
    
    @JsonProperty("prodi_id")
    private Integer idProdi;

    @JsonProperty("position")
    private String position = null;
}
