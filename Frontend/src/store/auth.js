import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token'),
    role: localStorage.getItem('userRole')
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    isTeacher: (state) => state.role === 'teacher',
    isStudent: (state) => state.role === 'student'
  },

  actions: {
    setUser(userData) {
      this.user = userData;
      this.token = userData.token;
      this.role = userData.role;
      localStorage.setItem('token', userData.token);
      localStorage.setItem('userRole', userData.role);
    },

    logout() {
      this.user = null;
      this.token = null;
      this.role = null;
      localStorage.removeItem('token');
      localStorage.removeItem('userRole');
    }
  }
});
