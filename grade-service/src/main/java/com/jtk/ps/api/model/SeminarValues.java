package com.jtk.ps.api.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seminar_values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeminarValues {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "seminar_criteria_id", nullable = false)
    @JsonProperty("seminar_criteria_id")
    private Integer seminarCriteriaId;

    @Column(name = "seminar_form_id", nullable = false)
    @JsonProperty("seminar_form_id")
    private Integer seminarFormId;
}
