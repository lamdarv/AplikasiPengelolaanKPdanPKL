package com.jtk.ps.api.dto;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDto {
    private Integer id;

    // @JsonProperty("name")
    private String name;

    private List<ParticipantDto> participants;

}
