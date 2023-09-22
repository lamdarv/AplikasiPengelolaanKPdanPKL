package com.jtk.ps.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "experience")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "course_name")
    @JsonProperty("course_name")
    private String courseName;

    @Column(name = "task_name")
    @JsonProperty("task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "tech_tool")
    @JsonProperty("tech_tool")
    private String techTool;

    @Column(name = "role_description")
    @JsonProperty("role_description")
    private String roleDescription;

    private String achievement;

    @Column(name = "lesson_learned")
    @JsonProperty("lesson_learned")
    private String lessonLearned;

    @Column(name = "time_description")
    @JsonProperty("time_description")
    private String timeDescription;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @JsonIgnore
    private CV cv;
}
