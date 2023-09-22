package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "prerequisite_jobscope")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrerequisiteJobscope {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "prerequisite_id")
    @JsonIgnore
    private Prerequisite prerequisite;

    @Column(name = "id_jobscope")
    @JsonProperty("id_jobscope")
    private Integer jobscopeId;
    
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
