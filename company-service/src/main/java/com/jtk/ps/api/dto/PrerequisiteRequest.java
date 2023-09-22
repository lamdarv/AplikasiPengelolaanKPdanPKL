package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrerequisiteRequest {
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

    @JsonProperty("company_id")
    private Integer company;

    @JsonProperty("region_id")
    private Integer regionId;

    @JsonProperty("competencies")
    private List<UpdateCompetenceJobscope> competencies;

    @JsonProperty("jobscopes")
    private List<UpdateCompetenceJobscope> jobscopes;
    
    @JsonProperty("project")
    private String project;
}
