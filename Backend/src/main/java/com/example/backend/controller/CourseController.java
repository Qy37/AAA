package com.example.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:5174")
public class CourseController {
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getCourses() {
        try {
            // 模拟课程数据
            List<Map<String, Object>> courses = new ArrayList<>();
            
            Map<String, Object> course1 = new HashMap<>();
            course1.put("id", "1");
            course1.put("name", "Java程序设计");
            courses.add(course1);
            
            Map<String, Object> course2 = new HashMap<>();
            course2.put("id", "2");
            course2.put("name", "数据结构");
            courses.add(course2);
            
            Map<String, Object> course3 = new HashMap<>();
            course3.put("id", "3");
            course3.put("name", "计算机网络");
            courses.add(course3);

            System.out.println("返回课程列表：" + courses); // 添加日志
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
