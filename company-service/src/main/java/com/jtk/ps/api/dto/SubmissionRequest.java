package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.Project;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequest {
    @JsonProperty("proposer")
    private String proposer;
    
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_mail")
    private String companyMail;

    @JsonProperty("address")
    private String address;

    @JsonProperty("no_phone")
    private String noPhone;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_phone")
    private String cpPhone;

    @JsonProperty("cp_mail")
    private String cpMail;

    @JsonProperty("cp_position")
    private String cpPosition;

    @JsonProperty("website")
    private String website;

    @JsonProperty("since_year")
    private Integer sinceYear;

    @JsonProperty("num_employee")
    private Integer numEmployee;

    @JsonProperty("recept_mechanism")
    private String receptMechanism;
    
    @JsonProperty("prodi")
    private String prodi;
    
    @JsonProperty("criteria")
    private List<Integer> criteria;

    @JsonProperty("advantages")
    private List<String> advantages;
    
    @JsonProperty("projects")
    private List<Project> projects;
}
