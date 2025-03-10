package com.backend.repository;

import com.backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByQuizSet_QuizsetId(Long quizsetId);

    @Query(value = """
        SELECT q.* FROM quiz q
        JOIN quizset qs ON q.quizset_id = qs.quizset_id
        WHERE qs.quiz_name LIKE CONCAT(:quizPrefix, '%')
        AND qs.quiz_diff = :difficulty
        """, nativeQuery = true)
    List<Quiz> findQuizzesByQuizNamePrefixAndDifficulty(
        @Param("quizPrefix") String quizPrefix,
        @Param("difficulty") Integer difficulty
    );
} 