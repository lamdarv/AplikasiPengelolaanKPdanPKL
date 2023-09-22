package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrerequisiteTableResponse {
    public PrerequisiteTableResponse(){
        this.jobScopes = new ArrayList<>();
    }

    private Integer id;

    @JsonProperty("company_name")
    private String companyName;

    private String address;

    @JsonProperty("work_system")
    private String workSystem;

    private Integer quota;

    @JsonProperty("job_scopes")
    private List<String> jobScopes;

    @JsonProperty("programming_languages")
    private String programmingLanguages;

    @JsonProperty("databases")
    private String databases;

    @JsonProperty("frameworks")
    private String frameworks;

    @JsonProperty("tools")
    private String tools;

    @JsonProperty("modelling_tools")
    private String modellingTools;

    @JsonProperty("communication_languages")
    private String communicationLanguages;

    private String description;

    private String facility;

    private String project;
}
