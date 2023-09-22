package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.CVJobScope;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CVInterestResponse {
    @JsonProperty("id_participant")
    private Integer idParticipant;

    @JsonProperty("id_domicile")
    private Integer idDomicile;

    @JsonProperty("jobscope")
    private List<CVJobScope> jobscope;

    @JsonProperty("competence")
    private List<CompetenceAndType> competence;

    public CVInterestResponse(){
        this.jobscope = new ArrayList<>();
        this.competence = new ArrayList<>();
    }
}
