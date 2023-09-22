package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ParticipantIdNameStatus {
    private Integer id;
    private String name;
    
    @JsonProperty("status_evaluated")
    private List<Integer> statusEvaluated = new ArrayList<>();
}
