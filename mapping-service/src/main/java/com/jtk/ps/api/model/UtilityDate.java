package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import javax.persistence.*;

@Data
@Table(name = "utility_date")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UtilityDate {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="id_utility")
    private Integer idUtility;

    @Column(name="prodi_id")
    private Integer prodiId;

    @Column(name="year")
    private Integer year;
    
    @Column(name="updated_at")
    private Date updatedAt;

}
