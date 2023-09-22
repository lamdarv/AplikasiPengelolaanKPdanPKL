package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "criteria_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaMapping {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "is_cost")
    private Boolean isCost;

    @Column(name = "percentage_value")
    private Float percentageValue;
}
