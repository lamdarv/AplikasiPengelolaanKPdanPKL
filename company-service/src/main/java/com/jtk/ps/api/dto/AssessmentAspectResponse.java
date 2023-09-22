package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AssessmentAspectResponse {
    private Integer id;

    @JsonProperty("aspect_name")
    private String aspectName;
}
