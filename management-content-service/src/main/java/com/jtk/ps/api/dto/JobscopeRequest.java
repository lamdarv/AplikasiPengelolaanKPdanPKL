package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobscopeRequest {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
