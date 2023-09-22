package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateCompetenceJobscope {
    @JsonProperty(value = "id")
    private Integer id;
    
    @JsonProperty(value = "prodi_id")
    private Integer prodiId;
}
