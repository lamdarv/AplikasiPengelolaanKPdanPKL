package com.jtk.ps.api.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "final_mapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalMapping {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer year;

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "participant_id")
    private Integer participantId;

    @Column(name = "company_id")
    private Integer companyId;
}
