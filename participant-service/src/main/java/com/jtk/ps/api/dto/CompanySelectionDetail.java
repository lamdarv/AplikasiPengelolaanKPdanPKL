package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanySelectionDetail {

    @JsonProperty("work_system")
    private String workSystem;

    private Integer priority1;

    private Integer priority2;

    private Integer priority3;

    private Integer priority4;

    private Integer priority5;
}
