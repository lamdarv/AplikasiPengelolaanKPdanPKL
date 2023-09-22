package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalMappingResponse {
    @JsonProperty("final_mapping")
    private List<FinalParticipantCompany> finalMapping = new ArrayList<>();
    
    @JsonProperty("is_final")
    private Integer isFinal;
    
    @JsonProperty("is_publish")
    private Integer isPublish;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("final_date")
    private String finalDate;
}
