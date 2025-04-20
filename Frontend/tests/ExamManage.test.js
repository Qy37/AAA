import { mount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import ExamManage from '../src/views/ExamManage.vue';

describe('ExamManage 组件测试', () => {
  it('创建试卷表单验证', async () => {
    const wrapper = mount(ExamManage);
    const submitBtn = wrapper.find('button[type="submit"]');
    await submitBtn.trigger('click');
    
    // 测试空表单提交
    expect(wrapper.emitted('error')).toBeTruthy();
    
    // 测试正确数据提交
    await wrapper.setData({
      newPaper: {
        title: '测试试卷',
        duration: 60,
        startTime: '2024-04-20 09:00:00',
        endTime: '2024-04-20 10:00:00',
        totalScore: 100,
        courseId: '1'
      }
    });
    
    await submitBtn.trigger('click');
    expect(wrapper.emitted('success')).toBeTruthy();
  });
});
