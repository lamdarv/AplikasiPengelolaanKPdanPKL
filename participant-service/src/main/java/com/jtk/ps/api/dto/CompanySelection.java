package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanySelection {
    @JsonProperty("participant_name")
    private String participantName;

    @JsonProperty("nim")
    private Integer nim;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("company_name")
    private List<String> companyName;
}
