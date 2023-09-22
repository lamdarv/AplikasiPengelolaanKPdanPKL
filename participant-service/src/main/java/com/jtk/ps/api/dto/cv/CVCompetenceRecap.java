package com.jtk.ps.api.dto.cv;

import lombok.Data;

@Data
public class CVCompetenceRecap {
    private Integer id;
    private Long total;

    public CVCompetenceRecap(Integer id, Long total) {
        this.id = id;
        this.total = total;
    }
}
