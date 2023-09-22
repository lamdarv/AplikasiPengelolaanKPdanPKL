package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seminar_criteria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarCriteria {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "criteria_name", nullable = false)
    private String criteriaName;

    @Column(name = "criteria_bobot", nullable = false)
    private Float criteriaBobot;

    @Column(name = "is_deleted", nullable = true)
    private Integer isDeleted;

    @Column(name = "is_selected", nullable = true)
    private Integer isSelected;
}
