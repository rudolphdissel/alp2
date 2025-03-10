package com.backend.repository;

import com.backend.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("SELECT u FROM Unit u JOIN FETCH u.subject s WHERE s.subject_id = :subjectId")
    List<Unit> findBySubjectId(@Param("subjectId") Long subjectId);
} 