package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "prerequisite_competence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrerequisiteCompetence {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "prerequisite_id")
    private Prerequisite prerequisite;

    @Column(name = "competence_id")
    private Integer competenceId;
    
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
