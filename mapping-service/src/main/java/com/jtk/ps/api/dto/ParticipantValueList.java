package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParticipantValueList {
    private Integer id;
    private String name;
    private Double ipk;

    @JsonProperty("course_name_and_value")
    List<CourseNameAndValue> courseNameAndValue;

    public ParticipantValueList() {
        this.courseNameAndValue = new ArrayList<>();
    }
}
