<template>
  <div class="exam-list">
    <h1>试卷列表</h1>
    <div class="exam-items">
      <el-card v-for="exam in exams" :key="exam.id" class="exam-item">
        <template #header>
          <h3>{{ exam.title }}</h3>
        </template>
        <p>时长：{{ exam.duration }}分钟</p>
        <p>开始时间：{{ formatDateTime(exam.startTime) }}</p>
        <p>结束时间：{{ formatDateTime(exam.endTime) }}</p>
        <p>总分：{{ exam.totalScore }}</p>
        <el-button type="primary" @click="goToDetail(exam.id)">查看详情</el-button>
      </el-card>
    </div>
  </div>
</template>

<script>
import axiosInstance from '../utils/axios-config';
import { formatDateTime } from '../utils/dateFormat';
import { ElMessage } from 'element-plus';

export default {
  name: 'ExamList',
  data() {
    return {
      exams: []
    };
  },
  methods: {
    formatDateTime,
    async loadExams() {
      try {
        const response = await axiosInstance.get('/exams');
        console.log('Exams data:', response.data);
        this.exams = response.data;
      } catch (error) {
        console.error('加载试卷列表失败:', error);
        ElMessage.error('加载试卷列表失败');
      }
    },
    goToDetail(examId) {
      this.$router.push(`/exam/${examId}`);
    }
  },
  mounted() {
    this.loadExams();
  }
};
</script>

<style scoped>
.exam-list {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}
.exam-items {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}
.exam-item {
  margin-bottom: 20px;
}
</style>
