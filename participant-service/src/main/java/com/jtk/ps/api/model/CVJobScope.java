package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cv_jobscope")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVJobScope {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @JsonIgnore
    private CV cv;

    @Column(name = "jobscope_id")
    @JsonProperty("id_jobscope")
    private Integer jobScopeId;
    
    @Column(name = "prodi_id")
    @JsonProperty("prodi_id")
    private Integer prodiId;
}
