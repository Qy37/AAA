-- 清空测试数据（可选）
-- DELETE FROM questions;
-- DELETE FROM exams;

-- 插入测试用户
INSERT INTO users (id, username, password, role) VALUES
('1', 'teacher1', '$2a$10$XXXXXX', 'TEACHER'),
('2', 'student1', '$2a$10$XXXXXX', 'STUDENT');

-- 插入考试数据
INSERT INTO exams (id, title, duration, start_time, end_time, total_score) VALUES 
('1', 'Java期末考试', 120, '2024-04-19 15:00:00', '2024-04-19 17:00:00', 100),
('2', '数据结构测试', 90, '2024-04-20 09:00:00', '2024-04-20 10:30:00', 100);

-- 插入试题数据
INSERT INTO questions (id, content, answer, score, exam_id) VALUES 
('1', '简述Spring Boot的优势', '1. 快速开发\n2. 自动配置\n3. 独立运行\n4. 丰富的starter', 20, '1'),
('2', '什么是依赖注入？', '依赖注入是Spring框架的核心特性之一，它可以让对象之间的依赖关系由Spring容器来管理', 20, '1'),
('3', '解释什么是二叉树', '二叉树是每个节点最多有两个子树的树结构，子树有左右之分', 25, '2'),
('4', '描述快速排序的基本原理', '选择基准元素，将数组分为两部分，递归排序', 25, '2');
