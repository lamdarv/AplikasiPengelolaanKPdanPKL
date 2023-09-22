package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrerequisiteCard {
    @JsonProperty("id_prerequisite")
    private Integer idPrerequisite;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("status_submission")
    private Boolean statusSubmission;

    @JsonProperty("status_prerequisite")
    private Boolean statusPrerequisite;
}
