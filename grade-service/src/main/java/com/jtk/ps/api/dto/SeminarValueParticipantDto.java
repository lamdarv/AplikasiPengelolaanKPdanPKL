package com.jtk.ps.api.dto;

import java.util.List;

import com.jtk.ps.api.model.SeminarValues;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarValueParticipantDto {
    private ParticipantDto peserta;

    private List<SeminarValues> nilai;

    private Float nilaiTotal;
}
