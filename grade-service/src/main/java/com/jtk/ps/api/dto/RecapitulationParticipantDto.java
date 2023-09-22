package com.jtk.ps.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecapitulationParticipantDto {
    
    @JsonProperty("id_participant")
    private Integer idParticipant;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nim")
    private String nim;
    
    @JsonProperty("component_data")
    private List<RecapitulationComponentDto> component_data;
    
    @JsonProperty("total_course")
    private Float total_course;

}
