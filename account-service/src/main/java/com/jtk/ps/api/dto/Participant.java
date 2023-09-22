package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @JsonProperty(value = "id_participant")
    private Integer idParticipant;

    @JsonProperty(value = "id_account")
    private Integer idAccount;

    @JsonProperty(value = "id_cv")
    private Integer idCv;

    private String name;

    @JsonProperty(value = "NIM")
    private Integer nim;

    @JsonProperty(value = "id_prodi")
    private Integer idProdi;

    @JsonProperty(value = "IPK")
    private float ipk;

    private boolean permission;
    private Integer year;
}
