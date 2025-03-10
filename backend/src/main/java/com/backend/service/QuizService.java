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
    public List<Quiz> getQuizzesByUnitAndDifficulty(String quizName, Long studentId, Long quizsetId) {
        // 학생의 현재 난이도 가져오기
        int currentDifficulty = studentDiffService.getStudentDiff(studentId, quizsetId);
        
        System.out.println("Quiz Name: " + quizName); // 디버깅용
        System.out.println("Current Difficulty: " + currentDifficulty); // 디버깅용
        
        List<Quiz> quizzes = quizRepository.findQuizzesByQuizNamePrefixAndDifficulty(quizName, currentDifficulty);
        
        System.out.println("Found Quizzes: " + quizzes.size()); // 디버깅용
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes found for difficulty level: " + currentDifficulty);
        } else {
            System.out.println("Sample quizzes:");
            quizzes.stream()
                .limit(3)
                .forEach(quiz -> {
                    System.out.println("- Quiz ID: " + quiz.getQuiz_id());
                    System.out.println("  Problem: " + quiz.getProblem());
                });
        }
        
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
    public void updateDifficulty(Long studentId, Long quizsetId, int totalQuestions, int correctAnswers) {
        double successRate = (double) correctAnswers / totalQuestions;
        int currentDiff = studentDiffService.getStudentDiff(studentId, quizsetId);
        
        int newDiff = currentDiff;
        if (successRate >= 0.5) {
            newDiff = Math.min(currentDiff + 1, 5);  // 최대 5
        } else {
            newDiff = Math.max(currentDiff - 1, 1);  // 최소 1
        }
        
        if (newDiff != currentDiff) {
            studentDiffService.updateStudentDiff(studentId, quizsetId, newDiff);
        }
    }
} 