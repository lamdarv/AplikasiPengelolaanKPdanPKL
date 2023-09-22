package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEvaluationRequest {
    @JsonProperty("num_evaluation")
    private Integer numEvaluation;
    
    private String comment;
    
    private List<ValuationRequest> valuation;

    private String position;
}
