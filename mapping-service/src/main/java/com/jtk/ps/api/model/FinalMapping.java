package com.jtk.ps.api.model;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private Integer year = Calendar.getInstance().get(Calendar.YEAR);

    @Column(name = "prodi_id")
    private Integer prodiId;

    @Column(name = "participant_id")
    private Integer participantId;

    @Column(name = "company_id")
    private Integer companyId;
}
