<template>
  <div class="student-exam">
    <div class="exam-header">
      <h1>{{ exam.title }}</h1>
      <div class="exam-timer">
        剩余时间: {{ formatTime(remainingTime) }}
      </div>
    </div>

    <div class="questions-container">
      <div v-for="(question, index) in exam.questions" :key="question.id" class="question-card">
        <h3>第 {{ index + 1 }} 题 ({{ question.score }}分)</h3>
        <p>{{ question.content }}</p>
        <div class="answer-area">
          <textarea 
            v-model="answers[question.id]" 
            :placeholder="'请在此输入答案...'"
            rows="4"
          ></textarea>
        </div>
      </div>
    </div>

    <div class="exam-footer">
      <button @click="submitExam" :disabled="submitted">提交试卷</button>
      <button @click="saveProgress">保存进度</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

export default {
  name: 'StudentExam',
  props: {
    examId: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const exam = ref({});
    const answers = ref({});
    const remainingTime = ref(0);
    const submitted = ref(false);
    let timer = null;

    const loadExam = async () => {
      try {
        const response = await axios.get(`/api/exams/${props.examId}/student`);
        exam.value = response.data;
        remainingTime.value = response.data.duration * 60;
        startTimer();
      } catch (error) {
        console.error('加载考试失败:', error);
      }
    };

    const startTimer = () => {
      timer = setInterval(() => {
        if (remainingTime.value > 0) {
          remainingTime.value--;
        } else {
          submitExam();
        }
      }, 1000);
    };

    const formatTime = (seconds) => {
      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const secs = seconds % 60;
      return `${hours}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    };

    const submitExam = async () => {
      if (submitted.value) return;
      try {
        await axios.post(`/api/exams/${props.examId}/submit`, {
          answers: answers.value
        });
        submitted.value = true;
      } catch (error) {
        console.error('提交考试失败:', error);
      }
    };

    const saveProgress = async () => {
      try {
        await axios.post(`/api/exams/${props.examId}/progress`, {
          answers: answers.value
        });
      } catch (error) {
        console.error('保存进度失败:', error);
      }
    };

    onMounted(() => {
      loadExam();
      window.addEventListener('beforeunload', saveProgress);
    });

    onBeforeUnmount(() => {
      if (timer) clearInterval(timer);
      window.removeEventListener('beforeunload', saveProgress);
    });

    return {
      exam,
      answers,
      remainingTime,
      submitted,
      formatTime,
      submitExam,
      saveProgress
    };
  }
};
</script>

<style scoped>
.student-exam {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.exam-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 10px;
  background: #f5f5f5;
  border-radius: 8px;
}

.exam-timer {
  font-size: 1.2em;
  color: #e74c3c;
  font-weight: bold;
}

.question-card {
  background: white;
  padding: 20px;
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.answer-area textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-top: 10px;
}

.exam-footer {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background: #ccc;
  cursor: not-allowed;
}
</style>
