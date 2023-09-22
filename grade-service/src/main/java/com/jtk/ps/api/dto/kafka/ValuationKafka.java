package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValuationKafka {
    
    private Integer id;

    private String aspectName;

    private Integer value;

    private Integer evaluation_id;

    private boolean is_core;

    private String operation;
}
