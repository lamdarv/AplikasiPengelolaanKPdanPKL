package com.jtk.ps.api.dto;

import lombok.Data;

@Data
public class PrerequisiteRecapResponse {
    private Integer id;
    private String name;
    private Long total;
    private Integer type;
}
