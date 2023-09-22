package com.jtk.ps.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarFormDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("date")
    private Date dateSeminar;

    @JsonProperty("participant_id")
    private Integer participantId;

    @JsonProperty("examiner_id")
    private Integer examinerId;

    @JsonProperty("examiner_type")
    private Integer examinerType;

    @JsonProperty("comment")
    private String comment;
}
