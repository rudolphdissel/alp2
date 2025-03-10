package com.backend.controller;

import com.backend.model.Quiz;
import com.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {
    
    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesByUnit(
            @RequestParam Long studentId,
            @RequestParam Long quizsetId,
            @RequestParam String quizName) {
        List<Quiz> quizzes = quizService.getQuizzesByUnitAndDifficulty(quizName, studentId, quizsetId);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/update-difficulty")
    public ResponseEntity<?> updateDifficulty(@RequestBody Map<String, Object> request) {
        Long studentId = Long.parseLong(request.get("student_id").toString());
        Long quizsetId = Long.parseLong(request.get("quizset_id").toString());
        int totalQuestions = Integer.parseInt(request.get("total_questions").toString());
        int correctAnswers = Integer.parseInt(request.get("correct_answers").toString());
        
        quizService.updateDifficulty(studentId, quizsetId, totalQuestions, correctAnswers);
        return ResponseEntity.ok().build();
    }
} 