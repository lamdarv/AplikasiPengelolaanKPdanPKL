package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {
    @JsonProperty("id_participant")
    private Integer idParticipant;

    private Integer nim;

    private String name;

    @JsonProperty("IPK")
    private float ipk;

    @JsonProperty("work_system")
    private String workSystem;

    private int year;

    @JsonProperty("status_cv")
    private Boolean statusCv;

    @JsonProperty("status_interest")
    private Boolean statusInterest;

    @JsonProperty("id_account")
    private int idAccount;

    @JsonProperty("id_cv")
    private int idCv;

    @JsonProperty("id_prodi")
    private int idProdi;
}
