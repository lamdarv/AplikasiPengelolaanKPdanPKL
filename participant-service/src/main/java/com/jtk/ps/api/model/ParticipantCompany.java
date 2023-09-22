package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "participant_company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantCompany {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer priority;

    @Column(name = "participant_id")
    @JsonProperty("participant_id")
    private int participantId;

    @Column(name = "company_id")
    @JsonProperty("company_id")
    private Integer companyId;
}
