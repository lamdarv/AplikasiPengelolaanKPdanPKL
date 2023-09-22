package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cv_competence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVCompetence {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @JsonIgnore
    private CV cv;

    @Column(name = "competence_id")
    @JsonProperty("id_competence")
    private Integer competenceId;

    @Column(name = "knowledge_id")
    @JsonProperty("id_knowledge")
    private Integer knowledgeId;
    
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
