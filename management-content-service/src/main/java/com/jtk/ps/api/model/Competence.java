package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "competence")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Competence {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pic", nullable = false)
    private String pic;

    @ManyToOne
    @JoinColumn(name = "competence_type_id", nullable = false)
    @JsonIgnore
    private CompetenceType competenceType;

    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
