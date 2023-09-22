package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "absence_recap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceRecap {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "participant_id")
    @JsonProperty("participant_id")
    private Integer participantId;

    @Column(name = "sakit")
    private Integer sick;

    @Column(name = "izin")
    private Integer excused;

    @Column(name = "alpa")
    private Integer unexcused;
}
