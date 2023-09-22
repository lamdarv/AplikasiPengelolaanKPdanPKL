package com.jtk.ps.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentAndCriteriasDto {
    
    private Integer id;

    private String name;

    @JsonProperty("is_average")
    private Integer isAverage;

    private List<CriteriaBodyDto> criteria_data;
}
