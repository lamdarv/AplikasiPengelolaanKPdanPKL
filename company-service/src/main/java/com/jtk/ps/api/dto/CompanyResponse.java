package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    @JsonProperty("id_company")
    private Integer idCompany;

    @JsonProperty("id_account")
    private Integer idAccount;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_email")
    private String companyEmail;

    @JsonProperty("address")
    private String address;

    @JsonProperty("telp")
    private String telp;

    @JsonProperty("cp_name")
    private String cpName;

    @JsonProperty("cp_telp")
    private String cpTelp;

    @JsonProperty("cp_email")
    private String cpEmail;

    @JsonProperty("cp_position")
    private String cpPosition;

    private String website;

    @JsonProperty("since_years")
    private Integer sinceYear;

    private Boolean status;

    private List<String> proposer;

    @JsonProperty("num_employee")
    private Integer numEmployee;

    @JsonProperty("lecturer_id")
    private Integer lecturerId;
}
