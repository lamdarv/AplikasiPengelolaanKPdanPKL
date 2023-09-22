package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormSubmitUpdateRequest {
    @JsonProperty("id_timeline")
    private Integer idTimeline;

    @JsonProperty("day_before")
    private Integer dayBefore;
}
