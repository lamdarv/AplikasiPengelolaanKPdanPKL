package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentAspectKafka {
    private Integer id;

    private String aspect_name;

    private Integer evaluation_form_id;

    private String operation;
}
