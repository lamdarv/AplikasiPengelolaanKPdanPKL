package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RankingAndDateResponse {
    private String date;

    @JsonProperty("ranking_list")
    List<RankingResponse> rankingList;
}
