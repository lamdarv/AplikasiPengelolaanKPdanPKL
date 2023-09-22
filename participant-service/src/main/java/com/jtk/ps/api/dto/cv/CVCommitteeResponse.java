package com.jtk.ps.api.dto.cv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CVCommitteeResponse {

    @JsonProperty("cv")
    private List<CVCommittee> cvCommittee;

    @JsonProperty("total_submitted")
    private int totalSubmitted;

    @JsonProperty("total_not_submitted")
    private int totalNotSubmitted;

}
