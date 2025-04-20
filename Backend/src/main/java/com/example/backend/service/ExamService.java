package com.example.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class ExamService {
    
    public List<Map<String, String>> getAllExams() {
        return List.of(
            Map.of("id", "1", "name", "Math Exam"),
            Map.of("id", "2", "name", "Science Exam")
        );
    }

    public Map<String, Object> createExam(Map<String, Object> examData) {
        // 创建新考试
        Map<String, Object> response = new HashMap<>();
        String examId = UUID.randomUUID().toString();
        
        response.put("id", examId);
        response.put("title", examData.get("title"));
        response.put("duration", examData.get("duration"));
        response.put("startTime", examData.get("startTime"));
        response.put("endTime", examData.get("endTime"));
        response.put("totalScore", examData.get("totalScore"));
        
        return response;
    }
}
