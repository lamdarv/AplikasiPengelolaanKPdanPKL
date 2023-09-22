package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorGradeResultKafka {
    
    private Integer id;

    private Integer value;

    private Integer supervisorGradeId;

    private Integer aspectId;

    private String operation;
}
