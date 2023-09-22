package com.jtk.ps.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "component_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentCourse {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bobot_component")
    private Integer bobotComponent;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Column(name = "is_average", nullable = false)
    private Integer isAverage;
}
