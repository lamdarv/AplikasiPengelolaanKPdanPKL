package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.PrerequisiteJobscope;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PrerequisiteResponse {
    private Integer id;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("practical_address")
    private String practicalAddress;

    @JsonProperty("in_advisor_name")
    private String inAdvisorName;

    @JsonProperty("in_advisor_position")
    private String inAdvisorPosition;

    @JsonProperty("in_advisor_mail")
    private String inAdvisorMail;

    @JsonProperty("facility")
    private String facility;

    @JsonProperty("total_d3")
    private Integer totalD3;

    @JsonProperty("total_d4")
    private Integer totalD4;

    @JsonProperty("work_system")
    private String workSystem;

    @JsonProperty("description")
    private String description;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("company_id")
    private Integer company;

    @JsonProperty("region_id")
    private Integer regionId;
    
    @JsonProperty("project")
    private String project;

    @JsonProperty("competencies_d3")
    private List<CompetenceAndType> competenciesD3 = new ArrayList<>();
    
    @JsonProperty("competencies_d4")
    private List<CompetenceAndType> competenciesD4 = new ArrayList<>();

    @JsonProperty("jobscopes_d3")
    private List<PrerequisiteJobscope> jobscopesD3 = new ArrayList<>();
    
    @JsonProperty("jobscopes_d4")
    private List<PrerequisiteJobscope> jobscopesD4 = new ArrayList<>();
}
