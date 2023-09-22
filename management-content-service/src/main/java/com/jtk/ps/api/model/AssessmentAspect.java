package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Table(name = "assessment_aspect")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AssessmentAspect {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aspect_name", nullable = false)
    @JsonProperty("aspect_name")
    private String aspectName;

    @ManyToOne
    @JoinColumn(name = "evaluation_form_id", nullable = false)
    @JsonIgnore
    private EvaluationForm evaluationForm;
}
