import axios from 'axios';

// 创建axios实例
const instance = axios.create({
  baseURL: 'http://localhost:8080/api', // 确保指向后端地址
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true // 允许发送Cookie
});

// 请求拦截器
instance.interceptors.request.use(
  config => {
    console.log('发送请求:', config.method?.toUpperCase(), config.url);
    return config;
  },
  error => {
    console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  response => {
    console.log('收到响应:', response.status, response.config.url);
    return response;
  },
  error => {
    console.error('响应错误:', error.response || error);
    return Promise.reject(error);
  }
);

export default instance;
