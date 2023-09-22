package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackCardResponse {
    @JsonProperty("id_company")
    private Integer idCompany;
    
    @JsonProperty("is_allowed_d3")
    private Boolean isAllowedD3 = false;
    
    @JsonProperty("is_more_than_timeline_d3")
    private Boolean isMoreThanTimelineD3 = true;
    
    @JsonProperty("status_submit_d3")
    private Integer statusSubmitD3 = 0;
    
    @JsonProperty("is_allowed_d4")
    private Boolean isAllowedD4 = false;
    
    @JsonProperty("is_more_than_timeline_d4")
    private Boolean isMoreThanTimelineD4 = true;
    
    @JsonProperty("status_submit_d4")
    private Integer statusSubmitD4 = 0;
}
