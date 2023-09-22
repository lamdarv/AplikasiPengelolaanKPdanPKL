package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompanySelectionRecap {
    private Integer id;
    @JsonProperty("company_name")
    private String companyName;
    private Long total;
    private Integer priority;
}
