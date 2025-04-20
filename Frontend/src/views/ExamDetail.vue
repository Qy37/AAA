<template>
  <div class="exam-detail">
    <el-card class="exam-info">
      <template #header>
        <h2>{{ exam.title }}</h2>
      </template>
      <p>时长：{{ exam.duration }}分钟</p>
      <p>开始时间：{{ formatDateTime(exam.startTime) }}</p>
      <p>结束时间：{{ formatDateTime(exam.endTime) }}</p>
      <p>总分：{{ exam.totalScore }}</p>
    </el-card>

    <!-- 试题列表 -->
    <el-card class="questions-list">
      <template #header>
        <div class="header-with-button">
          <h3>试题列表</h3>
          <el-button type="primary" @click="showAddQuestionDialog = true">添加试题</el-button>
        </div>
      </template>
      <div v-if="exam.questions && exam.questions.length > 0">
        <div v-for="(question, index) in exam.questions" :key="question.id" class="question-item">
          <div class="question-header">
            <span>第{{ index + 1 }}题（{{ question.score }}分）</span>
            <div class="question-actions">
              <el-button type="text" @click="editQuestion(question)">编辑</el-button>
              <el-button type="text" @click="deleteQuestion(question.id)" class="danger">删除</el-button>
            </div>
          </div>
          <div class="question-content">{{ question.content }}</div>
          <div class="question-answer">答案：{{ question.answer }}</div>
        </div>
      </div>
      <el-empty v-else description="暂无试题" />
    </el-card>

    <!-- 添加试题对话框 -->
    <el-dialog v-model="showAddQuestionDialog" title="添加试题">
      <el-form :model="newQuestion" label-width="100px">
        <el-form-item label="题目内容">
          <el-input type="textarea" v-model="newQuestion.content" rows="4" />
        </el-form-item>
        <el-form-item label="答案">
          <el-input type="textarea" v-model="newQuestion.answer" rows="2" />
        </el-form-item>
        <el-form-item label="分值">
          <el-input-number v-model="newQuestion.score" :min="1" :max="100" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddQuestionDialog = false">取消</el-button>
        <el-button type="primary" @click="addQuestion">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axiosInstance from '../utils/axios-config';
import { formatDateTime } from '../utils/dateFormat';
import { ElMessage } from 'element-plus';

export default {
  name: 'ExamDetail',
  setup() {
    const route = useRoute();
    const exam = ref({});
    const showAddQuestionDialog = ref(false);
    const newQuestion = ref({
      content: '',
      answer: '',
      score: 10
    });

    const loadExamDetail = async () => {
      try {
        const response = await axiosInstance.get(`/exams/${route.params.id}`);
        exam.value = response.data;
        // 确保questions数组存在
        exam.value.questions = exam.value.questions || [];
      } catch (error) {
        console.error('加载试卷详情失败:', error);
        ElMessage.error('加载试卷详情失败');
      }
    };

    const addQuestion = async () => {
      try {
        const response = await axiosInstance.post(
          `/exams/${route.params.id}/questions`,
          newQuestion.value
        );
        exam.value.questions.push(response.data);
        showAddQuestionDialog.value = false;
        ElMessage.success('添加试题成功');
        newQuestion.value = { content: '', answer: '', score: 10 };
      } catch (error) {
        ElMessage.error('添加试题失败');
      }
    };

    const editQuestion = (question) => {
      // TODO: 实现编辑功能
      ElMessage.info('编辑功能开发中');
    };

    const deleteQuestion = async (questionId) => {
      try {
        await axiosInstance.delete(`/exams/${route.params.id}/questions/${questionId}`);
        exam.value.questions = exam.value.questions.filter(q => q.id !== questionId);
        ElMessage.success('删除试题成功');
      } catch (error) {
        ElMessage.error('删除试题失败');
      }
    };

    onMounted(loadExamDetail);

    return {
      exam,
      formatDateTime,
      showAddQuestionDialog,
      newQuestion,
      addQuestion,
      editQuestion,
      deleteQuestion
    };
  }
};
</script>

<style scoped>
.exam-detail {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.exam-info, .questions-list {
  margin-bottom: 20px;
}

.header-with-button {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.question-item {
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 15px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.question-content {
  margin-bottom: 10px;
  font-size: 16px;
}

.question-answer {
  color: #666;
  font-size: 14px;
}

.question-actions {
  display: flex;
  gap: 10px;
}

.danger {
  color: #F56C6C;
}
</style>
