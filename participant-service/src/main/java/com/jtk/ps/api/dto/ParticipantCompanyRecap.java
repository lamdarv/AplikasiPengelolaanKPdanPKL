package com.jtk.ps.api.dto;

import lombok.Data;

@Data
public class ParticipantCompanyRecap {
    private Integer id;
    private Long total;
    private Integer priority;

    public ParticipantCompanyRecap(Integer id, Long total, Integer priority) {
        this.id = id;
        this.total = total;
        this.priority = priority;
    }
}
