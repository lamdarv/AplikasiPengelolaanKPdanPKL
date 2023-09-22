package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.dto.ranking.CompetenceCompany;
import com.jtk.ps.api.dto.ranking.JobscopeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReqSheetResponse {
    @JsonProperty("id_company")
    private Integer idCompany;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("domicile")
    private String domicile;

    @JsonProperty("jobscope")
    private List<JobscopeResponse> jobscope;

    @JsonProperty("competence")
    private List<CompetenceCompany> competence;
}