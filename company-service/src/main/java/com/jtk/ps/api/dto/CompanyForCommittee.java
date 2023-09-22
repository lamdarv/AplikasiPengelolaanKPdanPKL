package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyForCommittee {
    @JsonProperty("id_company")
    private Integer id;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_phone")
    private String cpPhone;

    @JsonProperty("cp_email")
    private String cpEmail;

    @JsonProperty("status_company")
    private Boolean statusCompany;

    @JsonProperty("status_prerequisite")
    private Boolean statusPrerequisite;
}
