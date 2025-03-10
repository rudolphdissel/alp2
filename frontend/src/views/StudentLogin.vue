<template>
  <div class="login-view">
    <h1>학생 로그인</h1>
    <div class="login-form">
      <input 
        type="text" 
        v-model="studentName" 
        placeholder="학생 이름을 입력하세요" 
        class="login-input"
        @keyup.enter="handleLogin"
      >
      <button @click="handleLogin" class="login-button">로그인</button>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'StudentLogin',
  data() {
    return {
      studentName: '',
      errorMessage: ''
    }
  },
  methods: {
    async handleLogin() {
      try {
        const response = await axiosInst.post('api/students/login', { student_name: this.studentName });
        if (response.data) {
          // 로컬 스토리지에 학생 정보 저장
          localStorage.setItem('student', JSON.stringify(response.data));
          // 과목 선택 페이지로 이동
          this.$router.push('/studentmenu');
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          this.errorMessage = '존재하지 않는 학생입니다.';
        } else {
          this.errorMessage = '로그인 중 오류가 발생했습니다.';
        }
      }
    }
  }
}
</script>

<style scoped>
.login-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 20px;
  background-color: #f5f5f5;
}

h1 {
  color: #333;
  margin-bottom: 40px;
  font-size: 2.5em;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 400px;
}

.login-input {
  padding: 15px;
  font-size: 1.2em;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.login-button {
  padding: 15px 30px;
  font-size: 1.2em;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.login-button:hover {
  background-color: #45a049;
}

.error-message {
  color: #ff0000;
  text-align: center;
  font-size: 1.1em;
}
</style> 