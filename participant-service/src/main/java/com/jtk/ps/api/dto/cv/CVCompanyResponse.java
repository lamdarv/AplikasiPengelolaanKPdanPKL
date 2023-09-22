package com.jtk.ps.api.dto.cv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CVCompanyResponse {

    @JsonProperty("id_cv")
    private Integer idCv;

    private String name;

    @JsonProperty("status_cv")
    private Boolean statusCv;
}
