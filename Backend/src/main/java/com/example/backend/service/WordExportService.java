package com.example.backend.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class WordExportService {
    public void createWordDocument(String filePath, String content) throws IOException {
        // 创建 Word 文档
        try (XWPFDocument document = new XWPFDocument()) {
            // 创建段落
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(content);

            // 写入文件
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        } catch (IOException e) {
            throw new IOException("Error while creating Word document: " + e.getMessage(), e);
        }
    }
}
