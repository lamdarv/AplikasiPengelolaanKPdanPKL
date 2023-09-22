package com.jtk.ps.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecapitulationResponseDto {
    
    List<SeminarValueParticipantDto> nilai_penguji_1;

    List<SeminarValueParticipantDto> nilai_penguji_2;

    List<SeminarValueParticipantDto> nilai_pembimbing;

    List<SeminarTotalValueDto> nilai_total_seminar;
}
