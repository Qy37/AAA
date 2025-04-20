package com.example.backend.controller;

import com.example.backend.model.Exam;
import com.example.backend.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public ResponseEntity<List<Exam>> getExams() {
        try {
            List<Exam> exams = examRepository.findAll();
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{examId}")
    public ResponseEntity<Map<String, Object>> getExamDetail(@PathVariable String examId) {
        try {
            Optional<Exam> exam = examRepository.findById(examId);
            if (exam.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                Exam examData = exam.get();
                response.put("id", examData.getId());
                response.put("title", examData.getTitle());
                response.put("duration", examData.getDuration());
                response.put("startTime", examData.getStartTime());
                response.put("endTime", examData.getEndTime());
                response.put("totalScore", examData.getTotalScore());
                response.put("questions", examData.getQuestions());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        try {
            Exam savedExam = examRepository.save(exam);
            return ResponseEntity.ok(savedExam);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("创建试卷失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable String examId) {
        try {
            if (!examRepository.existsById(examId)) {
                return ResponseEntity.notFound().build();
            }
            examRepository.deleteById(examId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除试卷失败：" + e.getMessage());
        }
    }

    @GetMapping("/{examId}/statistics")
    public ResponseEntity<?> getExamStatistics(@PathVariable String examId) {
        try {
            Map<String, Integer> stats = new HashMap<>();
            stats.put("below60", 5);
            stats.put("between60And70", 10);
            stats.put("between70And80", 15);
            stats.put("between80And90", 12);
            stats.put("above90", 8);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取统计信息失败：" + e.getMessage());
        }
    }
}
