package com.jtk.ps.api.dto.ranking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobscopeResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("id_jobscope")
    private Integer jobscopeId;

    @JsonProperty("prodi_id")
    private Integer prodiId;
}
