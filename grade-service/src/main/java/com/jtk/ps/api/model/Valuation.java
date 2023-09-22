package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "value")
    private Integer value;

    @Column(name = "evaluation_id")
    private Integer evaluationId;

    @Column(name = "is_core")
    private Integer isCore;
}
