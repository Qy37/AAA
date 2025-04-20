-- 创建数据库
CREATE DATABASE IF NOT EXISTS education_platform;

-- 使用数据库
USE education_platform;

-- 插入示例用户
INSERT INTO users (username, password, role) VALUES 
('student1', 'password1', 'STUDENT'),
('teacher1', 'password2', 'TEACHER');

-- 插入示例题目
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('What is 2 + 2?', '3', '4', '5', '6', 'B'),
('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'C');

-- 插入示例考试
INSERT INTO exams (name, start_time, end_time) VALUES 
('Math Exam', '2025-04-20 10:00:00', '2025-04-20 12:00:00');

-- 插入示例成绩
INSERT INTO exam_results (exam_id, student_id, score) VALUES 
(1, 1, 85);
