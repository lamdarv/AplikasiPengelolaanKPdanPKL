package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "utility")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utility {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
        
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;

    @Column(name = "is_final")
    @JsonProperty("is_final")
    private Integer isFinal;
}
