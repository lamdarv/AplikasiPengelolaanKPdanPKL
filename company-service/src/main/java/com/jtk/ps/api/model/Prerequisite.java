package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "prerequisite")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prerequisite {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("practical_address")
    @Column(name = "practical_address")
    private String practicalAddress;

    @JsonProperty("in_advisor_name")
    @Column(name = "in_advisor_name")
    private String inAdvisorName;

    @JsonProperty("in_advisor_position")
    @Column(name = "in_advisor_position")
    private String inAdvisorPosition;

    @JsonProperty("in_advisor_mail")
    @Column(name = "in_advisor_mail")
    private String inAdvisorMail;

    @JsonProperty("facility")
    @Column(name = "facility")
    private String facility;

    @JsonProperty("total_d3")
    @Column(name = "total_d3")
    private Integer totalD3 = 0;

    @JsonProperty("total_d4")
    @Column(name = "total_d4")
    private Integer totalD4 = 0;

    @JsonProperty("work_system")
    @Column(name = "work_system")
    private String workSystem;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("year")
    @Column(name = "year")
    private Integer year;

    @JsonProperty("status")
    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonProperty("company_id")
    private Company company;

    @JsonProperty("region_id")
    private Integer regionId;
    
    @JsonProperty("project")
    private String project;
}
