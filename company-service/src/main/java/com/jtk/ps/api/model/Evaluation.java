package com.jtk.ps.api.model;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "comment", nullable = true)
    private String comment;
    
    @Column(name = "year")
    private Integer year = Calendar.getInstance().get(Calendar.YEAR);

    @Column(name = "num_evaluation")
    private Integer numEvaluation;
    
    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "position")
    private String position;
    
    @Column(name = "prodi_id")
    private Integer idProdi;
    
    @Column(name = "company_id")
    private Integer idCompany;
    
    @Column(name = "participant_id")
    private Integer idParticipant;
}
