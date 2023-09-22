package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCardResponse {
    @JsonProperty("participant_d3")
    private List<EvaluationCard> participantD3 = new ArrayList<>();
    
    @JsonProperty("participant_d4")
    private List<EvaluationCard> participantD4 = new ArrayList<>();
    
    @JsonProperty("is_more_than_timeline_d3")
    private Boolean isMoreThanTimelineD3;
    
    @JsonProperty("is_more_than_timeline_d4")
    private Boolean isMoreThanTimelineD4;
}
