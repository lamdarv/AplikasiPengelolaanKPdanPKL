package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarCriteriaRequestDto {
    
    @JsonProperty("criteria_name")
    private String criteriaName;

    @JsonProperty("criteria_bobot")
    private Float criteriaBobot;

    @JsonProperty("is_selected")
    private Integer isSelected;
}
