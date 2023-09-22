package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "participant")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Participant {

    @Id
    @Column(unique = true, nullable = false)
    private int id;

    private String name;

    private Double ipk;

    @JsonProperty("work_system")
    private String workSystem;

    private int year;

    @JsonProperty("status_cv")
    private Boolean statusCv;

    @JsonProperty("status_interest")
    private Boolean statusInterest;

    @Column(name = "account_id")
    private int accountId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private CV cv;

    @Column(name = "prodi_id")
    private int prodiId;
}
