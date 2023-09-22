package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "criteria_component_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaComponentCourse {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_form", nullable = false)
    private String nameForm;

    @Column(name = "type_form", nullable = false)
    private String typeForm;

    @Column(name = "bobot_criteria", nullable = false)
    private Integer bobotCriteria;

    @Column(name = "component_id", nullable = false)
    private Integer componentId;

    @Column(name = "industry_criteria_id")
    private Integer industryCriteriaId;

    @Column(name = "seminar_criteria_id")
    private Integer seminarCriteriaId;

    @Column(name = "self_assessment_criteria_id")
    private Integer selfAssessmentCriteriaId;

    @Column(name = "supervisor_criteria_id")
    private Integer supervisorCriteriaId;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted;

}
