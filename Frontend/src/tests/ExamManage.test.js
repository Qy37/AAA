import { mount } from '@vue/test-utils';
import ExamManage from '../views/ExamManage.vue';

describe('ExamManage', () => {
  test('创建试卷', async () => {
    const wrapper = mount(ExamManage);
    // 模拟表单输入
    // 验证创建API调用
    // ...existing test code...
  });

  test('成绩统计图表', async () => {
    const wrapper = mount(ExamManage);
    // 验证饼图渲染
    // 验证数据计算
    // ...existing test code...
  });
});
