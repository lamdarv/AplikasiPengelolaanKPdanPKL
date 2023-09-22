package com.jtk.ps.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeminarFormRequestDto {

    @JsonProperty("participant_id")
    private Integer participantId;
    
    @JsonProperty("date_seminar")
    private Date dateSeminar;

    @JsonProperty("examiner_id")
    private Integer examinerId;

    @JsonProperty("examiner_type")
    private Integer examinerType;

    @JsonProperty("comment")
    private String comment;
}
