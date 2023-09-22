package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "championship")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Championship {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer year;

    @Column(name = "championship_name")
    @JsonProperty("championship_name")
    private String championshipName;

    @Column(name = "achievement")
    private String achievement;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @JsonIgnore
    private CV cv;
}
