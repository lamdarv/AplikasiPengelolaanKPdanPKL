package com.jtk.ps.api.model;


import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "self_assessment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfAssessment {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "finish_date")
    private Date finish_date;

    @Column(name = "participant_id")
    private Integer participantId;
}
