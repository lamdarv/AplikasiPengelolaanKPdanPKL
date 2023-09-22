package com.jtk.ps.api.model;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "year")
    private Integer year = Calendar.getInstance().get(Calendar.YEAR);
    
    @Column(name = "status")
    private Integer status = 0;
    
    @Column(name = "prodi_id")
    private Integer idProdi;
    
    @Column(name = "company_id")
    private Integer idCompany;
}
