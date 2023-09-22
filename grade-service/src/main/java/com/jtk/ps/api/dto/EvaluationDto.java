package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto {

    private Integer id;

    private String comment;

    private Integer year;

    private Integer numEvaluation;

    private Integer status;

    private String position;

    private Integer prodiId;

    private Integer companyId;

    private Integer participantId;
}
