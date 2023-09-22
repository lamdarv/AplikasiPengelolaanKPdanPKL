package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalMappingKafka {
    private Integer id;

    private Integer year;

    private Integer prodi_id;

    private Integer participant_id;

    private Integer company_id;

    private String operation;
}
