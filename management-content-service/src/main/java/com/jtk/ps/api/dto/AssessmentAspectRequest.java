package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssessmentAspectRequest {
    @JsonProperty("aspect_name")
    private String aspectName;

    @JsonProperty("evaluation_form_id")
    private Integer evaluationFormId;
}
