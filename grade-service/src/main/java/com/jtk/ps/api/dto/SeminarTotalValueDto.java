package com.jtk.ps.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarTotalValueDto {
    
    private ParticipantDto participant;

    private Float nilaiTotal;
}
