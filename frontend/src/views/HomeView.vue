<template>
  <div class="home">
    <h1>학생 관리 시스템</h1>
    <div class="button-container">
      <router-link to="/teacherlogin" class="login-button teacher">
        교사 로그인
      </router-link>
      <router-link to="/studentlogin" class="login-button student">
        학생 로그인
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home {
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

.button-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.login-button {
  padding: 15px 30px;
  font-size: 1.2em;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  text-align: center;
  min-width: 200px;
}

.teacher {
  background-color: #4CAF50;
  color: white;
}

.teacher:hover {
  background-color: #45a049;
}

.student {
  background-color: #4CAF50;
  color: white;
}

.student:hover {
  background-color: #45a049;
}
</style>