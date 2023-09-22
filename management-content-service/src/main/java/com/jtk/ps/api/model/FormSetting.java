package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "form_setting")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FormSetting {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name="prodi_id")
    private Integer prodiId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "timeline_setting_id")
    private TimelineSetting timelineSetting;

    @Column(name="day_before")
    private Integer dayBefore;

}
