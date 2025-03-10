package com.backend.controller;

import com.backend.service.StudentDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student-diff")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentDiffController {

    @Autowired
    private StudentDiffService studentDiffService;

    @PostMapping("/increment-score")
    public ResponseEntity<Void> incrementScore(@RequestBody Map<String, Long> request) {
        studentDiffService.incrementScore(request.get("student_id"), request.get("unit_id"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-score")
    public ResponseEntity<Void> resetScore(@RequestBody Map<String, Long> request) {
        studentDiffService.resetScore(request.get("student_id"), request.get("unit_id"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/score")
    public ResponseEntity<Map<String, Integer>> getScore(
            @RequestParam Long student_id,
            @RequestParam Long unit_id) {
        int score = studentDiffService.getScore(student_id, unit_id);
        return ResponseEntity.ok(Map.of("score", score));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Map<String, Object>>> getScoresByTeacher(@PathVariable Long teacherId) {
        List<Map<String, Object>> scores = studentDiffService.getScoresByTeacherId(teacherId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/teacher/{teacherId}/subject/{subjectId}")
    public ResponseEntity<List<Map<String, Object>>> getScoresByTeacherAndSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {
        List<Map<String, Object>> scores = studentDiffService.getScoresByTeacherIdAndSubjectId(teacherId, subjectId);
        return ResponseEntity.ok(scores);
    }
} 