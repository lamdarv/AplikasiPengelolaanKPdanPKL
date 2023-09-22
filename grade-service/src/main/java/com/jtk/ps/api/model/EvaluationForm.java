package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluation_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationForm {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "num_evaluation")
    private Integer numEvaluation;
}
