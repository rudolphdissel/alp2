package com.backend.controller;

import com.backend.model.Quiz;
import com.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {
    
    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getQuizzes(
            @RequestParam Long studentId,
            @RequestParam Long unitId,
            @RequestParam String quizName) {
        System.out.println("=== Quiz Controller Request ===");
        System.out.println("Student ID: " + studentId);
        System.out.println("Unit ID: " + unitId);
        System.out.println("Quiz Name: " + quizName);
        
        List<Quiz> quizzes = quizService.getQuizzesByUnitAndDifficulty(quizName, studentId, unitId);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitQuiz(@RequestBody Map<String, Object> request) {
        Long studentId = Long.parseLong(request.get("studentId").toString());
        Long unitId = Long.parseLong(request.get("unitId").toString());
        int totalQuestions = Integer.parseInt(request.get("totalQuestions").toString());
        int correctAnswers = Integer.parseInt(request.get("correctAnswers").toString());
        
        quizService.updateDifficulty(studentId, unitId, totalQuestions, correctAnswers);
        return ResponseEntity.ok().build();
    }
} 