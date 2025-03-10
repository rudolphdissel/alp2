package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_diff")
public class StudentDiff {
    @EmbeddedId
    private StudentDiffId id;

    @Column(name = "set_diff")
    private Integer set_diff;

    @Column(name = "score")
    private Integer score = 0;
}

