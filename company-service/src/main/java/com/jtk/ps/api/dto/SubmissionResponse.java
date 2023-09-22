package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SubmissionResponse {
    @JsonProperty("id")
    private Integer id;
    
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_phone")
    private String cpPhone;

    @JsonProperty("cp_mail")
    private String cpMail;
}
