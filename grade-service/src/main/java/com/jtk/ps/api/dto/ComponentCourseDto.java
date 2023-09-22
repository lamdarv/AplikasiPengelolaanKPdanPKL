package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentCourseDto {
    
    private Integer id;

    private String name;

    @JsonProperty("bobot_component")
    private Integer bobotComponent;

    @JsonProperty("course_id")
    private Integer courseId;

    @JsonProperty("is_average")
    private Integer isAverage;
}
