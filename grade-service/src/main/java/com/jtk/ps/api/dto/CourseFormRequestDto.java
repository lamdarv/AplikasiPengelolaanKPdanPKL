package com.jtk.ps.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFormRequestDto {
    
    @JsonProperty("prodi_id")
    private Integer prodiId;

    @JsonProperty("kode")
    private String kode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tahun_ajaran_start")
    private Integer tahunAjaranStart;

    @JsonProperty("tahun_ajaran_end")
    private Integer tahunAjaranEnd;

    @JsonProperty("sks")
    private Integer sks;
}
