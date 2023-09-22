package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaBodyDto {
    
    private Integer id;

    @JsonProperty("name_form")
    private String nameForm;

    @JsonProperty("type_form")
    private String typeForm;

    @JsonProperty("component_id")
    private Integer componentId;

    @JsonProperty("bobot_criteria")
    private Integer bobotCriteria;

    @JsonProperty("aspect_form_id")
    private Integer aspectFormId;

    @JsonProperty("aspect_name")
    private String aspectName;

}
