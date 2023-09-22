package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "proposer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proposer {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "proposer_name")
    private String proposerName;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company companyId = null;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submissionId;
}
