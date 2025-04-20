package com.example.backend.controller;

import com.example.backend.service.WordExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    private WordExportService wordExportService;

    @GetMapping("/generate")
    public String generateWord(@RequestParam String filePath, @RequestParam String content) {
        try {
            wordExportService.createWordDocument(filePath, content);
            return "Word document created at: " + filePath;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
