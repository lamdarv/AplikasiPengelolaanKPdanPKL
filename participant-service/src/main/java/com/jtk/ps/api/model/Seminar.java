package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "seminar")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seminar {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer year;

    @Column(name = "seminar_name")
    @JsonProperty("seminar_name")
    private String seminarName;

    @Column(name = "role_description")
    @JsonProperty("role_description")
    private String roleDescription;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @JsonIgnore
    private CV cv;
}
