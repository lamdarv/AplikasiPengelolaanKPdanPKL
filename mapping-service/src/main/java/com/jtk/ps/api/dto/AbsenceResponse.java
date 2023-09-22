package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AbsenceResponse {
    private Integer id;

    @JsonProperty("participant_id")
    private Integer participantId;

    @JsonProperty("sick")
    private Integer sick;

    @JsonProperty("excused")
    private Integer excused;

    @JsonProperty("unexcused")
    private Integer unexcused;
}
