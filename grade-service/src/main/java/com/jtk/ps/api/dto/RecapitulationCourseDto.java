package com.jtk.ps.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecapitulationCourseDto {
    
    @JsonProperty("id_course")
    private Integer idCourse;

    @JsonProperty("name_course")
    private String nameCourse;

    @JsonProperty("kode_course")
    private String kode;

    @JsonProperty("participant_data")
    List<RecapitulationParticipantDto> participant_data;
}
