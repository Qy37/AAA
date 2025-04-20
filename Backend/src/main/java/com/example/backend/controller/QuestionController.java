package com.example.backend.controller;

import com.example.backend.model.Question;
import com.example.backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/exams/{examId}/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<?> getQuestions(@PathVariable String examId) {
        try {
            return ResponseEntity.ok(questionService.getQuestionsByExamId(examId));
        } catch (Exception e) {
            logger.error("获取试题列表失败", e);
            return ResponseEntity.badRequest().body("获取试题失败: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(@PathVariable String examId, @RequestBody Question question) {
        try {
            Question savedQuestion = questionService.addQuestion(examId, question);
            return ResponseEntity.ok(savedQuestion);
        } catch (Exception e) {
            logger.error("添加试题失败", e);
            return ResponseEntity.badRequest().body("添加试题失败: " + e.getMessage());
        }
    }
}
