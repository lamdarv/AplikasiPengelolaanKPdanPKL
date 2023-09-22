package com.jtk.ps.api.dto;

import lombok.Data;

@Data
public class PrerequisiteJobScopeRecap {
    private Integer id;
    private Long total;

    public PrerequisiteJobScopeRecap(Integer id, Long total) {
        this.id = id;
        this.total = total;
    }
}
