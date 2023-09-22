package com.jtk.ps.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "criteria_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    
    private String attribute;

    @Column(name = "percentage_value")
    private Integer percentageValue;
}
