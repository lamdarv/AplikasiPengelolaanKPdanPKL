package com.jtk.ps.api.dto.jsonpolban;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.List;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class ParticipantJsonResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("nim")
    private String nim;

    @JsonProperty("ipk")
    private Double ipk;

    @JsonProperty("prodi")
    private String prodi;

    @Embedded
    @JsonProperty("mata_kuliah")
    private List<CourseCodeValue> course;

    @JsonProperty("sakit")
    private Integer sick;

    @JsonProperty("izin")
    private Integer excused;

    @JsonProperty("alpa")
    private Integer unexcused;
}
