package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalMappingRequest {
    @JsonProperty("company_id")
    private Integer companyId;
    
    @JsonProperty("participant_id")
    private Integer participantId;
}
