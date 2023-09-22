package com.jtk.ps.api.model;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseValues {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value")
    private Float value;

    @Column(name = "criteria_id")
    private Integer criteriaId;

    @Column(name = "mentor_values_id")
    private Integer mentorValuesId;

    @Column(name = "self_assessment_values_id")
    private Integer selfAssessmentValuesId;

    @Column(name = "seminar_values_id")
    private Integer seminarValuesId;

    @Column(name = "industry_values_id")
    private Integer industryValuesId;

    @Column(name = "participant_id")
    private Integer participantId;

    @Column(name = "created_date")
    private LocalDate created_date;
}
