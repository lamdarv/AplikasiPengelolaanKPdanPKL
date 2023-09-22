package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CourseNameAndValue {
    private Integer id;
    private String name;
    private Integer value;

    @JsonProperty("course_id")
    private Integer courseId;
}
