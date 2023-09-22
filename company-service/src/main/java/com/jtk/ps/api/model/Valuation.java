package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "valuation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Valuation {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aspect_name")
    private String aspectName;
    
    private Integer value;

    @Column(name = "is_core")
    private Boolean isCore;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;
}
