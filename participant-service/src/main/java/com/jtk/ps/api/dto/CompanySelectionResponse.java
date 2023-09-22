package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanySelectionResponse {
    @JsonProperty("company_selection")
    private List<CompanySelection> companySelection;

    @JsonProperty("total_selected")
    private int totalSubmitted;

    @JsonProperty("total_not_selected")
    private int totalNotSubmitted;
}
