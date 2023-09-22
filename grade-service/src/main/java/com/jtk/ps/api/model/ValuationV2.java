package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "valuation_v2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValuationV2 {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aspect_name")
    private String aspectName;

    @Column(name = "numeric_value")
    private Integer numericValue;

    @Column(name = "letter_value")
    private String letterValue;

    @Column(name = "justification")
    private String justification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", referencedColumnName = "id")
    private Evaluation evaluation;

    @Column(name = "is_core")
    private Integer isCore;
}
