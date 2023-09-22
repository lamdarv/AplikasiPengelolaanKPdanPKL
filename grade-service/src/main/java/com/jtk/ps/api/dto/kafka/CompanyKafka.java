package com.jtk.ps.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyKafka {
    private Integer id;

    private String company_name;

    private String company_email;

    private Integer since_year;

    private boolean status;

    private Integer account_id;

    private String operation;

}
