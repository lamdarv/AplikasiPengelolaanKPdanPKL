package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyIdNameStatus {
    private Integer id;
    private String name;
    
    @JsonProperty("status_feedback")
    private Integer statusFeedback = 0;
}
