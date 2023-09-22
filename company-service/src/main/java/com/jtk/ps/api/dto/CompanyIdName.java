package com.jtk.ps.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyIdName {
    private Integer id;
    private String name;
    private Integer quota = 0;
}
