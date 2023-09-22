package com.jtk.ps.api.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "participant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "status_cv", nullable = false)
    private Integer statusCv;

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "account_id", nullable = false)
    @JsonProperty("account_id")
    private Integer accountId;
}
