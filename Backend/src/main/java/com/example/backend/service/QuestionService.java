package com.example.backend.service;

import com.example.backend.model.Question;
import com.example.backend.model.Exam;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.repository.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionService(QuestionRepository questionRepository, ExamRepository examRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    @Transactional
    public Question addQuestion(String examId, Question question) {
        return examRepository.findById(examId).map(exam -> {
            question.setExam(exam);
            return questionRepository.save(question);
        }).orElseThrow(() -> new RuntimeException("试卷不存在"));
    }

    public List<Question> getQuestionsByExamId(String examId) {
        return questionRepository.findByExamId(examId);
    }
}
