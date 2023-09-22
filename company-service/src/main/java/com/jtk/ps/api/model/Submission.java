package com.jtk.ps.api.model;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "submission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_mail")
    private String companyMail;

    @Column(name = "address")
    private String address;

    @Column(name = "no_phone")
    private String noPhone;

    @Column(name = "cp_name")
    private String cpName;

    @Column(name = "cp_phone")
    private String cpPhone;

    @Column(name = "cp_mail")
    private String cpMail;

    @Column(name = "cp_position")
    private String cpPosition;

    @Column(name = "website")
    private String website;

    @Column(name = "since_year")
    private Integer sinceYear;

    @Column(name = "num_employee")
    private Integer numEmployee;

    @Column(name = "recept_mechanism")
    private String receptMechanism;

    @Column(name = "prodi")
    private String prodi;

    @Column(name = "year_kp_pkl")
    private Integer yearKpPkl = Calendar.getInstance().get(Calendar.YEAR);

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
