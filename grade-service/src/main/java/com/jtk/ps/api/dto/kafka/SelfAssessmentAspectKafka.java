package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelfAssessmentAspectKafka {
    private Integer id;

    private String name;

    private String description;

    private String operation;
}
