package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "feedback_question")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FeedbackQuestion {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "prodi_id", nullable = false)
    private Integer prodiId;
}
