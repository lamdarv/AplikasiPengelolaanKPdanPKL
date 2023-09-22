package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValuationRequest {
    @JsonProperty("aspect_name")
    private String aspectName;
    
    private Integer value;
    
    @JsonProperty("is_core")
    private Boolean isCore;
}
