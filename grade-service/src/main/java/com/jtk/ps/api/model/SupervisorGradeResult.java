package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supervisor_grade_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorGradeResult {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grade")
    private Integer value;

    @Column(name = "supervisor_grade_id")
    private Integer supervisorGradeId;

    @Column(name = "id_aspect_grade")
    private Integer aspectId;
}
