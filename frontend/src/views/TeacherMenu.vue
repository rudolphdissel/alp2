<template>
  <div class="teacher-menu">
    <h1>교사 메뉴</h1>
    <div class="menu-buttons">
      <button @click="goToStudentManage" class="menu-button">
        학생 관리
      </button>
      <button @click="goToScores" class="menu-button">
        성적 조회
      </button>
      <router-link to="/" class="menu-button">
        홈으로
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TeacherMenu',
  created() {
    const teacherId = localStorage.getItem('teacherId');
    if (!teacherId) {
      this.$router.push('/teacherlogin');
    }
  },
  methods: {
    goToStudentManage() {
      this.$router.push('/students');
    },
    goToScores() {
      this.$router.push('/teachersubject');
    }
  }
}
</script>

<style scoped>
.teacher-menu {
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

.menu-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 400px;
}

.menu-button {
  padding: 15px 30px;
  font-size: 1.2em;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.menu-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

.menu-button.disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.menu-button.disabled:hover {
  background-color: #cccccc;
}
</style>