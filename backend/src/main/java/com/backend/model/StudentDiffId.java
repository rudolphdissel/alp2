package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDiffId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "student_id")
    private Long student_id;

    @Column(name = "quizset_id")
    private Long quizset_id;
} 