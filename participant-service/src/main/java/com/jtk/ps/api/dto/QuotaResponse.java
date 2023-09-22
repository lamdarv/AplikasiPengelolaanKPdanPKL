package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotaResponse {
    @JsonProperty("id_company")
    private Integer idCompany;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quota_d3")
    private Integer quotaD3;

    @JsonProperty("quota_d4")
    private Integer quotaD4;
}
