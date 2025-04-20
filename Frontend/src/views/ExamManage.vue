<template>
  <div class="exam-manage">
    <h1>试卷管理</h1>
    
    <!-- 试卷创建表单 -->
    <div class="create-paper">
      <h2>创建新试卷</h2>
      <div class="form-group">
        <el-form :model="newPaper" label-width="100px">
          <el-form-item label="试卷标题">
            <el-input v-model="newPaper.title" placeholder="请输入试卷标题" />
          </el-form-item>
          
          <el-form-item label="考试时长">
            <el-input-number v-model="newPaper.duration" :min="1" placeholder="考试时长(分钟)" />
          </el-form-item>
          
          <el-form-item label="开始时间">
            <el-date-picker
              v-model="newPaper.startTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="选择开始时间"
            />
          </el-form-item>
          
          <el-form-item label="结束时间">
            <el-date-picker
              v-model="newPaper.endTime"
              type="datetime"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="选择结束时间"
            />
          </el-form-item>
          
          <el-form-item label="总分">
            <el-input-number v-model="newPaper.totalScore" :min="0" placeholder="总分" />
          </el-form-item>
          
          <el-form-item label="选择课程">
            <el-select v-model="newPaper.courseId" placeholder="请选择课程">
              <el-option
                v-for="course in courses"
                :key="course.id"
                :label="course.name"
                :value="course.id"
              />
            </el-select>
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="createPaper">创建试卷</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!-- 试卷列表 -->
    <div class="paper-list">
      <div v-for="paper in papers" :key="paper.id" class="paper-item">
        <h3>{{ paper.title }}</h3>
        <div class="paper-info">
          <p>时长：{{ paper.duration }}分钟</p>
          <p>开始时间：{{ formatDateTime(paper.startTime) }}</p>
          <p>结束时间：{{ formatDateTime(paper.endTime) }}</p>
          <p>总分：{{ paper.totalScore }}</p>
          <p>状态：{{ getPaperStatus(paper) }}</p>
        </div>
        <div class="paper-actions">
          <button @click="editPaper(paper.id)">编辑</button>
          <button @click="viewResults(paper.id)">查看成绩</button>
          <button @click="deletePaper(paper.id)">删除</button>
        </div>
      </div>
    </div>

    <!-- 成绩统计图表 -->
    <div v-if="showChart" class="score-statistics">
      <h2>成绩统计</h2>
      <div class="chart-container">
        <pie-chart :data="scoreDistribution" />
      </div>
    </div>
  </div>
</template>

<script>
import axiosInstance from '../utils/axios-config';
import PieChart from '../components/PieChart.vue';
import { formatDateTime } from '../utils/dateFormat';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

export default {
  name: 'ExamManage',
  components: {
    PieChart
  },
  setup() {
    const router = useRouter();
    return { router };
  },
  data() {
    return {
      papers: [],
      courses: [],
      newPaper: {
        title: '',
        duration: 60,
        startTime: '',
        endTime: '',
        totalScore: 100,
        courseId: ''
      },
      showChart: false,
      scoreDistribution: []
    };
  },
  methods: {
    formatDateTime,
    async loadCourses() {
      try {
        console.log('开始加载课程列表');
        const response = await axiosInstance.get('/courses');
        console.log('课程列表数据:', response.data);
        if (Array.isArray(response.data)) {
          this.courses = response.data;
          if (this.courses.length === 0) {
            ElMessage.warning('没有可用的课程');
          }
        } else {
          ElMessage.error('课程数据格式错误');
        }
      } catch (error) {
        console.error('加载课程列表失败:', error.response || error);
        ElMessage.error('加载课程列表失败: ' + (error.response?.data || error.message));
      }
    },
    async createPaper() {
      try {
        if (!this.validateForm()) {
          return;
        }
        
        // 修正日期格式
        const payload = {
          title: this.newPaper.title,
          duration: parseInt(this.newPaper.duration),
          startTime: new Date(this.newPaper.startTime).toISOString(),
          endTime: new Date(this.newPaper.endTime).toISOString(),
          totalScore: parseInt(this.newPaper.totalScore)
        };

        console.log('Sending payload:', payload);
        const response = await axiosInstance.post('/exams', payload);
        ElMessage.success('创建试卷成功！');
        this.papers.push(response.data);
        this.resetForm();
        this.$router.push('/exams');
      } catch (error) {
        console.error('创建试卷失败:', error);
        const errorMessage = error.response?.data || error.message || '未知错误';
        ElMessage.error(`创建试卷失败：${errorMessage}`);
      }
    },
    validateForm() {
      if (!this.newPaper.title) {
        ElMessage.warning('请输入试卷标题');
        return false;
      }
      if (!this.newPaper.startTime) {
        ElMessage.warning('请选择开始时间');
        return false;
      }
      if (!this.newPaper.endTime) {
        ElMessage.warning('请选择结束时间');
        return false;
      }
      if (!this.newPaper.courseId) {
        ElMessage.warning('请选择课程');
        return false;
      }
      if (new Date(this.newPaper.endTime) <= new Date(this.newPaper.startTime)) {
        ElMessage.warning('结束时间必须晚于开始时间');
        return false;
      }
      return true;
    },
    async viewResults(paperId) {
      try {
        const response = await axiosInstance.get(`/exams/${paperId}/statistics`);
        this.scoreDistribution = [
          { name: '<60分', value: response.data.below60 },
          { name: '60-70分', value: response.data.between60And70 },
          { name: '70-80分', value: response.data.between70And80 },
          { name: '80-90分', value: response.data.between80And90 },
          { name: '≥90分', value: response.data.above90 }
        ];
        this.showChart = true;
      } catch (error) {
        console.error('获取成绩统计失败:', error);
        ElMessage.error('获取成绩统计失败');
      }
    },
    async editPaper(paperId) {
      try {
        this.$router.push(`/exam/${paperId}/edit`);
      } catch (error) {
        ElMessage.error('跳转编辑页面失败');
      }
    },
    async deletePaper(paperId) {
      try {
        await axiosInstance.delete(`/exams/${paperId}`);
        this.papers = this.papers.filter(p => p.id !== paperId);
        ElMessage.success('删除试卷成功');
      } catch (error) {
        ElMessage.error('删除试卷失败');
      }
    },
    async loadPapers() {
      try {
        console.log('开始加载试卷列表');
        const response = await axiosInstance.get('/exams');
        console.log('试卷列表数据:', response.data);
        this.papers = response.data;
      } catch (error) {
        console.error('加载试卷列表失败:', error.response || error);
        ElMessage.error('加载试卷列表失败: ' + (error.response?.data || error.message));
      }
    },
    resetForm() {
      this.newPaper = {
        title: '',
        duration: 60,
        startTime: '',
        endTime: '',
        totalScore: 100,
        courseId: ''
      };
    },
    getPaperStatus(paper) {
      const now = new Date();
      const startTime = new Date(paper.startTime);
      const endTime = new Date(paper.endTime);
      if (now < startTime) {
        return '未开始';
      } else if (now >= startTime && now <= endTime) {
        return '进行中';
      } else {
        return '已结束';
      }
    }
  },
  mounted() {
    this.loadPapers();
    this.loadCourses();
  }
};
</script>

<style scoped>
.exam-manage {
  padding: 20px;
}

.create-paper {
  background: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.form-group {
  display: grid;
  gap: 10px;
  margin: 10px 0;
}

.paper-list {
  display: grid;
  gap: 20px;
  margin: 10px 0;
}

.paper-item {
  border: 1px solid #ddd;
  padding: 15px;
  border-radius: 4px;
}

.paper-info {
  margin: 10px 0;
}

.paper-actions {
  display: flex;
  gap: 10px;
}

.chart-container {
  height: 400px;
  margin-top: 20px;
}

.el-form {
  max-width: 600px;
  margin: 0 auto;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input,
.el-input-number,
.el-select,
.el-date-picker {
  width: 100%;
}
</style>
