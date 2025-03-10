<template>
  <div class="teacher-login">
    <h1>교사 로그인</h1>
    <div class="login-form">
      <div class="form-group">
        <label for="teacherName">교사 이름</label>
        <input 
          type="text" 
          id="teacherName" 
          v-model="teacherName" 
          placeholder="이름을 입력하세요"
          required
        >
      </div>
      <button @click="handleLogin" class="submit-button">
        로그인
      </button>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'TeacherLogin',
  data() {
    return {
      teacherName: ''
    }
  },
  methods: {
    async handleLogin() {
      if (!this.teacherName.trim()) {
        alert('이름을 입력해주세요.');
        return;
      }

      try {
        const response = await axiosInst.post('/teachers/', {
          teacherName: this.teacherName.trim()
        });

        localStorage.setItem('teacherId', response.data.teacherId);
        this.$router.push('/teachermenu');
      } catch (error) {
        console.error('Error:', error);
        alert('서버 오류가 발생했습니다.');
      }
    }
  }
}
</script>

<style scoped>
.teacher-login {
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
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  color: #333;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1em;
}

input:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

.submit-button {
  width: 100%;
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1.1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #45a049;
}
</style> 