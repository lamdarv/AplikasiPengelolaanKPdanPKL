package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackTableResponse {
    @JsonProperty("is_published")
    private Boolean isPublished = false;
    
    private List<CompanyIdNameStatus> companyList = new ArrayList<>();

    @JsonProperty("total_submitted")
    private Integer totalSubmitted;
    
    @JsonProperty("total_not_submitted")
    private Integer totalNotSubmitted;
}
