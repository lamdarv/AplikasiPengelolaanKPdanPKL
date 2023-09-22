package com.jtk.ps.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "feedback_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackAnswer {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "question")
    private String question;
    
    @Column(name = "value")
    private Integer value;
    
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
}
