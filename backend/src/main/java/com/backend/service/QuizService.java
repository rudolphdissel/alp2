package com.backend.service;

import com.backend.model.Quiz;
import com.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private StudentDiffService studentDiffService;

    private String getQuizPrefix(String unitName) {
        switch (unitName.toLowerCase()) {
            case "일반화학": return "nchem";
            case "유기화학": return "ochem";
            case "물리화학": return "pchem";
            case "분석화학": return "achem";
            default: return "";
        }
    }

    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByUnitAndDifficulty(String quizName, Long studentId, Long unitId) {
        // 학생의 현재 난이도 가져오기
        int currentDifficulty = studentDiffService.getStudentDiff(studentId, unitId);
        
        List<Quiz> quizzes = quizRepository.findQuizzesByUnitIdAndDifficulty(unitId, currentDifficulty);
        
        return quizzes.stream()
            .map(quiz -> {
                Quiz newQuiz = new Quiz();
                newQuiz.setQuiz_id(quiz.getQuiz_id());
                newQuiz.setProblem(quiz.getProblem());
                newQuiz.setRight_answer(quiz.getRight_answer());
                return newQuiz;
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateDifficulty(Long studentId, Long unitId, int totalQuestions, int correctAnswers) {
        double successRate = (double) correctAnswers / totalQuestions;
        int currentDiff = studentDiffService.getStudentDiff(studentId, unitId);
        
        int newDiff = currentDiff;
        if (successRate >= 0.5) {
            newDiff = Math.min(currentDiff + 1, 5);  // 최대 5
        } else {
            newDiff = Math.max(currentDiff - 1, 1);  // 최소 1
        }
        
        if (newDiff != currentDiff) {
            studentDiffService.updateStudentDiff(studentId, unitId, newDiff);
        }
    }
} 