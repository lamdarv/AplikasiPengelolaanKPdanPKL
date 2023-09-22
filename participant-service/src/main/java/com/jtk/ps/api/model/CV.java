package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cv")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CV {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nickname;

    private String address;

    @Column(name = "no_phone")
    private String noPhone;

    private String email;

    private String religion;

    private Character gender;

    private String place;

    private Date birthday;

    private Boolean marriage;

    private String citizenship;

    @Column(name = "domicile_id")
    private Integer domicileId;

}
