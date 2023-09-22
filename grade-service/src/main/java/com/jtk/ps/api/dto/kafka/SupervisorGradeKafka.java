package com.jtk.ps.api.dto.kafka;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorGradeKafka {
    private Integer id;

    private Integer phase;

    private Integer participantId;

    private Date date;

    private Integer supervisorGradeId;

    private String operation;
}
