package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantValue {
    @JsonProperty("participant_name")
    private String participantName;

    @JsonProperty("result")
    private Double result;

}
