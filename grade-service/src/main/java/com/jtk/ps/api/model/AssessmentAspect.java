package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assessment_aspect")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentAspect {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aspect_name")
    private String aspectName;

    @Column(name = "evaluation_form_id")
    private Integer evaluationFormId;

    @Column(name = "is_delete")
    private Integer isDelete;
}
