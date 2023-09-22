package com.jtk.ps.api.dto.kafka;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class TimelineKafka {
    private Integer id;

    private String name;

    private Date start_date;

    private Date end_date;

    private Integer prodi_id;

    private String description;

    private String operation;
}
