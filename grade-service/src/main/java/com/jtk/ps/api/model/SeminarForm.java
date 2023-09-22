package com.jtk.ps.api.model;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seminar_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarForm {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_seminar", nullable = true)
    private Date dateSeminar;

    @Column(name = "participant_id", nullable = false)
    private Integer participantId;

    @Column(name = "examiner_id")
    private Integer examinerId;
    
    @Column(name = "examiner_type", nullable = true)
    private Integer examinerType;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "is_finalization")
    private Integer isFinalization;

    @Column(name = "total_value")
    private Float totalValue;
}
