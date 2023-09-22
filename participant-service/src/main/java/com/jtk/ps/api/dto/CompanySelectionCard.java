package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanySelectionCard {

    @JsonProperty("status_update")
    private Boolean statusUpdate;

    @JsonProperty("status_submission")
    private Boolean statusSubmission;
}
