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
public class EvaluationTableResponse {
    private List<ParticipantIdNameStatus> participantList = new ArrayList<>();

    @JsonProperty("total_submitted")
    private List<Integer> totalSubmitted = new ArrayList<>();
    
    @JsonProperty("total_not_submitted")
    private List<Integer> totalNotSubmitted = new ArrayList<>();
}
