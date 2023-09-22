package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySelection {
    private int id;

    private Integer priority;

    @JsonProperty("participant_id")
    private int participantId;

    @JsonProperty("company_id")
    private int companyId;
}
