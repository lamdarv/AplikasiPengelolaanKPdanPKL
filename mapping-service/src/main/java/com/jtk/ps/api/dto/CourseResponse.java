package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CourseResponse {
    private Integer id;

    private String name;

    @JsonProperty("prodi_id")
    private Integer prodiId;
}
