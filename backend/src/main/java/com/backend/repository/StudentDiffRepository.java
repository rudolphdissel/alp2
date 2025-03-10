package com.backend.repository;

import com.backend.model.StudentDiff;
import com.backend.model.StudentDiffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDiffRepository extends JpaRepository<StudentDiff, StudentDiffId> {
    
    @Query(value = """
        SELECT s.student_name, sub.subject_name, u.unit_name, 
               sd.score, COUNT(q.quiz_id) as total_questions
        FROM student s
        JOIN student_diff sd ON s.student_id = sd.student_id
        JOIN quizset qs ON sd.quizset_id = qs.quizset_id
        JOIN unit u ON qs.unit_id = u.unit_id
        JOIN subject sub ON u.subject_id = sub.subject_id
        LEFT JOIN quiz q ON qs.quizset_id = q.quizset_id
        WHERE s.teacher_id = :teacherId
        GROUP BY s.student_name, sub.subject_name, u.unit_name, sd.score
        """, nativeQuery = true)
    List<Object[]> findScoresByTeacherId(@Param("teacherId") Long teacherId);

    @Query(value = """
        SELECT s.student_name, u.unit_name, 
               sd.score, COUNT(q.quiz_id) as total_questions
        FROM student s
        JOIN student_diff sd ON s.student_id = sd.student_id
        JOIN quizset qs ON sd.quizset_id = qs.quizset_id
        JOIN unit u ON qs.unit_id = u.unit_id
        JOIN subject sub ON u.subject_id = sub.subject_id
        LEFT JOIN quiz q ON qs.quizset_id = q.quizset_id
        WHERE s.teacher_id = :teacherId AND sub.subject_id = :subjectId
        GROUP BY s.student_name, u.unit_name, sd.score
        ORDER BY s.student_name, u.unit_name
        """, nativeQuery = true)
    List<Object[]> findScoresByTeacherIdAndSubjectId(
        @Param("teacherId") Long teacherId,
        @Param("subjectId") Long subjectId);
} 