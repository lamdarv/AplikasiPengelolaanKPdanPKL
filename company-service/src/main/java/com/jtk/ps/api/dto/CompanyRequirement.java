package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.PrerequisiteJobscope;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyRequirement {
    @JsonProperty("id_company")
    private Integer idCompany;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("id_domicile")
    private Integer idDomicile;

    @JsonProperty("jobscope")
    private List<PrerequisiteJobscope> jobscope;

    @JsonProperty("competence")
    private List<CompetenceAndType> competence;

    public CompanyRequirement() {
        this.jobscope = new ArrayList<>();
        this.competence = new ArrayList<>();
    }

}
