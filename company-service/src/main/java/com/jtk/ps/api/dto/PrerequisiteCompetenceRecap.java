package com.jtk.ps.api.dto;

import lombok.Data;

@Data
public class PrerequisiteCompetenceRecap {
    private Integer id;
    private Long total;

    public PrerequisiteCompetenceRecap(Integer id, Long total) {
        this.id = id;
        this.total = total;
    }
}
