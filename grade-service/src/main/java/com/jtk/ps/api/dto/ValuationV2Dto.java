package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValuationV2Dto {
    private Integer id;

    private String aspectName;

    private Integer numericValue;

    private String letterValue;

    private String justification;

    private Integer evaluationId;
}
