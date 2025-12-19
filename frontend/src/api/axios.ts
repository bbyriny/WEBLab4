import axios, { type InternalAxiosRequestConfig } from 'axios';
import router from '../router/index';

const api = axios.create({
  baseURL: 'http://localhost:3213/api'
});

api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = localStorage.getItem('authToken');
  
  if (token) {
  
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error: any) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('authToken');
      router.push('/auth/login');
    }
    return Promise.reject(error);
  }
);

export default api;