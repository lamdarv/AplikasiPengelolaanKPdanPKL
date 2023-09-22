package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ListCompany {
    private List<CompanyForCommittee> company;

    @JsonProperty("total_company_active")
    private Integer totalCompanyActive;

    @JsonProperty("total_company_inactive")
    private Integer totalCompanyInactive;

    @JsonProperty("total_prerequisite_submitted")
    private Integer totalPrerequisiteSubmitted;

    @JsonProperty("total_prerequisite_notsubmitted")
    private Integer totalPrerequisiteNotSubmitted;
}
