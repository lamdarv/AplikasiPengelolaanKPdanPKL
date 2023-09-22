package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarValuesDto {
    
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("value")
    private Float value;

    @JsonProperty("seminar_criteria_id")
    private Integer seminarCriteriaId;

    @JsonProperty("seminar_form_id")
    private Integer seminarFormId;


}
