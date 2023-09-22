package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "address")
    private String address;

    @Column(name = "no_phone")
    private String noPhone;

    @Column(name = "cp_name", nullable = false)
    private String cpName;

    @Column(name = "cp_phone", nullable = false)
    private String cpPhone;

    @Column(name = "cp_email", nullable = false)
    private String cpEmail;

    @Column(name = "cp_position")
    private String cpPosition;

    @Column
    private String website;

    @Column(name = "num_employee")
    private Integer numEmployee;

    @Column(name = "since_year")
    private Integer sinceYear;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "account_id", nullable = false)
    @JsonProperty("account_id")
    private Integer accountId;

    @Column(name ="lecturer_id")
    private Integer lecturerId;
}
