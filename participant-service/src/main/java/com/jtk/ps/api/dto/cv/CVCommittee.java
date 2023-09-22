package com.jtk.ps.api.dto.cv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CVCommittee {
    private String name;
    private Integer nim;

    @JsonProperty("status_cv")
    private Boolean statusCv;

    @JsonProperty("id_cv")
    private Integer idCv;
}
