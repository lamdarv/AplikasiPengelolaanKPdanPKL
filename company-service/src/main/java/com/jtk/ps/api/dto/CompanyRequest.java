package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class
CompanyRequest {
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_email")
    private String companyEmail;

    @JsonProperty("address")
    private String address;

    @JsonProperty("no_phone")
    private String noPhone;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_phone")
    private String cpPhone;

    @JsonProperty("cp_email")
    private String cpEmail;

    @JsonProperty("cp_position")
    private String cpPosition;

    private String website;

    @JsonProperty("num_employee")
    private Integer numEmployee;

    @JsonProperty("since_year")
    private Integer sinceYear;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("lecturer_id")
    private Integer lecturerId;
}
