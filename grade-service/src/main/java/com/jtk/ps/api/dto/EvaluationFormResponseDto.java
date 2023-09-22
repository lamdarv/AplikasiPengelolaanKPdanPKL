package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvaluationFormResponseDto {
    
    @JsonProperty("form_type")
    private String formType;

    @JsonProperty("form_name")
    private String formName;
}
