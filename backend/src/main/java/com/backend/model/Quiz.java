package com.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quiz_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizset_id", nullable = false)
    private QuizSet quizSet;

    @Column(name = "problem", nullable = false)
    private String problem;

    @Column(name = "right_answer", nullable = false)
    private String right_answer;
} 