package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "year")
    private Integer year;

    @Column(name = "num_evaluation")
    private Integer numEvaluation;

    @Column(name = "status")
    private Integer status;

    @Column(name = "position")
    private String position;

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "participant_id")
    private Integer participantId;
}
