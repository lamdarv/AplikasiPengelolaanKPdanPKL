package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "jobscope")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Jobscope {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pic", nullable = false)
    private String pic;
    
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
