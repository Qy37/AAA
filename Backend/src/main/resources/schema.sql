-- 删除现有表（如果存在）
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS exam_results;
DROP TABLE IF EXISTS exams;
DROP TABLE IF EXISTS users;

-- 创建用户表
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- 创建考试表
CREATE TABLE exams (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    total_score INT NOT NULL
);

-- 创建试题表
CREATE TABLE questions (
    id VARCHAR(36) PRIMARY KEY,
    content TEXT NOT NULL,
    answer TEXT NOT NULL,
    score INT NOT NULL,
    exam_id VARCHAR(36),
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE
);

-- 创建考试结果表
CREATE TABLE exam_results (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36),
    exam_id VARCHAR(36),
    score INT,
    submit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);
