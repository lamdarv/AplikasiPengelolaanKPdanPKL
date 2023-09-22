package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseForm {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "prodi_id", nullable = false)
    private Integer prodiId;

    @Column(name = "kode",nullable = false)
    private String kode;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "tahun_ajaran_start")
    private Integer tahunAjaranStart;

    @Column(name = "tahun_ajaran_end")
    private Integer tahunAjaranEnd;

    @Column(name = "sks")
    private Integer sks;

    @Column(name = "is_finalization")
    private Integer isFinalization;
}
