package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantValueResponse {
    private Integer id;
    private String name;
    private Integer value;
    private Integer courseId;
    private Integer idParticipant;
    private String nameParticipant;
    private Double ipkParticipant;

}
