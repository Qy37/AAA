import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import ExamList from '../views/ExamList.vue';
import ExamDetail from '../views/ExamDetail.vue';
import ExamManage from '../views/ExamManage.vue';
import StudentExam from '../views/StudentExam.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/exams', name: 'ExamList', component: ExamList },
  { path: '/exam/:id', name: 'ExamDetail', component: ExamDetail },
  { 
    path: '/exam-manage', 
    name: 'ExamManage', 
    component: ExamManage,
    meta: { requiresAuth: true, roles: ['teacher'] }
  },
  { 
    path: '/student/exam/:examId', 
    name: 'StudentExam', 
    component: StudentExam,
    props: true,
    meta: { requiresAuth: true, roles: ['student'] }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
