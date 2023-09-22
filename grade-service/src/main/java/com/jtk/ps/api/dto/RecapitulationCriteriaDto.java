package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecapitulationCriteriaDto {
    
    @JsonProperty("id_criteria")
    private Integer idCriteria;

    @JsonProperty("name_form")
    private String nameForm;

    @JsonProperty("form_type")
    private String formType;

    @JsonProperty("name_aspect")
    private String nameAspect;

    @JsonProperty("bobot")
    private Integer bobot;

    @JsonProperty("value")
    private Float value;
}
