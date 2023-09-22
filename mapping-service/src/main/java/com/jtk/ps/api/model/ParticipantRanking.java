package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "participant_ranking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRanking {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    private Double value;

    private Integer year;

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "participant_id")
    @JsonProperty("participant_id")
    private Integer participantId;

    @Column(name = "company_id")
    @JsonProperty("company_id")
    private Integer companyId;
}
