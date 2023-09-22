package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReqResponse {
    @JsonProperty("id_company")
    private Integer idCompany;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("id_domicile")
    private Integer idDomicile;

    @JsonProperty("jobscope")
    private List<JobscopeResponse> jobscope;

    @JsonProperty("competence")
    private List<CompetenceCompany> competence;
}
