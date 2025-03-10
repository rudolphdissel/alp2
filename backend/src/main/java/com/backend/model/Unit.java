package com.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unit_id;

    @Column(name = "unit_name", nullable = false)
    private String unit_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @JsonManagedReference
    @OneToMany(mappedBy = "unit", fetch = FetchType.EAGER)
    private List<QuizSet> quizSets;
} 