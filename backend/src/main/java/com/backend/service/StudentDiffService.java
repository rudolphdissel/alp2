package com.backend.service;

import com.backend.model.StudentDiff;
import com.backend.model.StudentDiffId;
import com.backend.repository.StudentDiffRepository;
import com.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentDiffService {
    
    @Autowired
    private StudentDiffRepository studentDiffRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public void incrementScore(Long studentId, Long quizsetId) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        StudentDiff studentDiff = studentDiffRepository.findById(id)
            .orElseGet(() -> {
                StudentDiff newDiff = new StudentDiff();
                newDiff.setId(id);
                newDiff.setScore(0);
                return newDiff;
            });
        
        studentDiff.setScore(studentDiff.getScore() + 1);
        studentDiffRepository.save(studentDiff);
    }

    @Transactional(readOnly = true)
    public int getScore(Long studentId, Long quizsetId) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        return studentDiffRepository.findById(id)
            .map(StudentDiff::getScore)
            .orElse(0);
    }

    @Transactional
    public void initializeScore(Long studentId, Long quizsetId) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        if (!studentDiffRepository.existsById(id)) {
            StudentDiff newDiff = new StudentDiff();
            newDiff.setId(id);
            newDiff.setScore(0);
            studentDiffRepository.save(newDiff);
        }
    }

    @Transactional
    public void resetScore(Long studentId, Long quizsetId) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        StudentDiff studentDiff = studentDiffRepository.findById(id)
            .orElseGet(() -> {
                StudentDiff newDiff = new StudentDiff();
                newDiff.setId(id);
                newDiff.setSet_diff(3); // 처음 시도할 때만 난이도 3으로 설정
                return newDiff;
            });
        studentDiff.setScore(0); // 점수만 초기화
        studentDiffRepository.save(studentDiff);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getScoresByTeacherId(Long teacherId) {
        List<Object[]> results = studentDiffRepository.findScoresByTeacherId(teacherId);
        
        return results.stream().map(result -> {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("studentName", (String) result[0]);
            scoreMap.put("subjectName", (String) result[1]);
            scoreMap.put("unitName", (String) result[2]);
            scoreMap.put("score", ((Number) result[3]).longValue());
            scoreMap.put("totalQuestions", ((Number) result[4]).longValue());
            
            double percentage = ((Number) result[4]).longValue() > 0 
                ? (((Number) result[3]).longValue() * 100.0) / ((Number) result[4]).longValue()
                : 0.0;
            
            scoreMap.put("percentage", percentage);
            return scoreMap;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getScoresByTeacherIdAndSubjectId(Long teacherId, Long subjectId) {
        List<Object[]> results = studentDiffRepository.findScoresByTeacherIdAndSubjectId(teacherId, subjectId);
        
        return results.stream().map(result -> {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("studentName", (String) result[0]);
            scoreMap.put("unitName", (String) result[1]);
            scoreMap.put("score", ((Number) result[2]).longValue());
            scoreMap.put("totalQuestions", ((Number) result[3]).longValue());
            
            double percentage = ((Number) result[3]).longValue() > 0 
                ? (((Number) result[2]).longValue() * 100.0) / ((Number) result[3]).longValue()
                : 0.0;
            
            scoreMap.put("percentage", percentage);
            return scoreMap;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int getStudentDiff(Long studentId, Long quizsetId) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        return studentDiffRepository.findById(id)
            .map(StudentDiff::getSet_diff)
            .orElse(3); // 기본값 3
    }

    @Transactional
    public void updateStudentDiff(Long studentId, Long quizsetId, int newDiff) {
        StudentDiffId id = new StudentDiffId(studentId, quizsetId);
        StudentDiff studentDiff = studentDiffRepository.findById(id)
            .orElseGet(() -> {
                StudentDiff newStudentDiff = new StudentDiff();
                newStudentDiff.setId(id);
                return newStudentDiff;
            });
        studentDiff.setSet_diff(newDiff);
        studentDiffRepository.save(studentDiff);
    }
} 