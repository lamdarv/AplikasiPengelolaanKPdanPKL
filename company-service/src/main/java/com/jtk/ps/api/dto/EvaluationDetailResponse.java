package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDetailResponse {
    private Integer numEvaluation;

    @JsonProperty("valuation_core")
    private List<ValuationRequest> valuationCore = new ArrayList<>();

    @JsonProperty("valuation_non_core")
    private List<ValuationRequest> valuationNonCore = new ArrayList<>();
    
    private String comment;

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

    @JsonProperty("position")
    private String position;

    @JsonProperty("start_date")
    private String startDate;

    private Integer nim;
}
