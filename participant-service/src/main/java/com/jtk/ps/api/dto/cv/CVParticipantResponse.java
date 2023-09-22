package com.jtk.ps.api.dto.cv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CVParticipantResponse {

    @JsonProperty("id_cv")
    private Integer idCV;

    @JsonProperty("status_update")
    private Boolean statusUpdate;

    @JsonProperty("status_submit")
    private Boolean statusSubmit;
}
