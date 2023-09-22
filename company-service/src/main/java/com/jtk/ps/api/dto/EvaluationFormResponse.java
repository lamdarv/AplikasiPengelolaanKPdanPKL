package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EvaluationFormResponse {
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_address")
    private String companyAddress;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_email")
    private String cpEmail;

    @JsonProperty("cp_phone")
    private String cpPhone;

    @JsonProperty("assessment_aspect")
    private List<String> assessmentAspect;

    @JsonProperty("start_date")
    private String startDate;
}
