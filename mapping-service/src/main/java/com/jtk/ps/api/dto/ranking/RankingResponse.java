package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.dto.ranking.ParticipantValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RankingResponse {
    @JsonProperty("company_name")
    private String companyName;

    private Integer quota;

    @JsonProperty("participants")
    private List<ParticipantValue> participants;

}
