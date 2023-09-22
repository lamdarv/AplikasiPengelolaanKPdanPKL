package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorGradeAspectKafka {
    private Integer id;

    private String description;

    private Float gradeWeight;

    private String operation;
}
