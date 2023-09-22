package com.jtk.ps.api.dto.cv;

import lombok.Data;

@Data
public class CVJobScopeRecap {
    private Integer id;
    private Long total;

    public CVJobScopeRecap(Integer id, Long total) {
        this.id = id;
        this.total = total;
    }
}
