package com.jtk.ps.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantDto {

    private Integer id;

    private String name;

    private Integer year;

    private Integer status_cv;

    private Integer prodi_id;

    private Integer account_id;

    private String nim;
}
