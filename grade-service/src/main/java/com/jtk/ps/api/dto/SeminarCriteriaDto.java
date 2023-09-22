package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarCriteriaDto {

    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("criteria_name")
    private String criteriaName;

    @JsonProperty("criteria_bobot")
    private Float criteriaBobot;

    @JsonProperty("is_selected")
    private Integer isSelected;
}
