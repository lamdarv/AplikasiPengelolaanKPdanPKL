package com.jtk.ps.api.dto.jsonpolban;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class CourseCodeValue {
    @JsonProperty("kode")
    private String code;

    @JsonProperty("nama")
    private String name;

    @JsonProperty("nilai")
    private Integer value;
}
