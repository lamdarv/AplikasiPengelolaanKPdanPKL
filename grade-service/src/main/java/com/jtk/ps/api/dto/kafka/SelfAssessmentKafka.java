package com.jtk.ps.api.dto.kafka;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelfAssessmentKafka {
    private Integer id;

    private Date start_date;

    private Date finish_date;

    private Integer participant_id;

    private String operation;
}
