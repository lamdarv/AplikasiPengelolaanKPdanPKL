package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedbackRequest {
    @JsonProperty("company_id")
    private Integer idCompany;
    
    @JsonProperty("prodi_id")
    private Integer idProdi;

    @JsonProperty("year")
    private Integer year = Calendar.getInstance().get(Calendar.YEAR);
    
    @JsonProperty("status")
    private Integer status = 0;
}
