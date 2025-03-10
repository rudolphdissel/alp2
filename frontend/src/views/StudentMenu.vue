<template>
  <div class="menu-view">
    <h1>과목 선택</h1>
    <div class="subject-buttons">
      <router-link 
        v-for="subject in subjects" 
        :key="subject.subject_id"
        :to="{ path: '/unit', query: { subjectId: subject.subject_id }}"
        class="subject-button"
        :class="{ active: subject.subject_name === '화학' }"
      >
        {{ subject.subject_name }}
      </router-link>
    </div>
    <router-link to="/" class="home-button">홈으로 돌아가기</router-link>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'StudentMenu',
  data() {
    return {
      subjects: []
    }
  },
  async created() {
    try {
      const response = await axiosInst.get('/subjects');
      this.subjects = response.data;
    } catch (error) {
      console.error('과목 목록을 가져오는데 실패했습니다:', error);
    }
  }
}
</script>

<style scoped>
.menu-view {
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

.subject-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 400px;
  margin-bottom: 30px;
}

.subject-button {
  padding: 15px 30px;
  font-size: 1.2em;
  background-color: #cccccc;
  color: #666666;
  border: none;
  border-radius: 8px;
  cursor: not-allowed;
  transition: all 0.3s ease;
}

.subject-button.active {
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.subject-button.active:hover {
  background-color: #45a049;
}

.home-button {
  margin-top: 20px;
  padding: 15px 30px;
  font-size: 1.2em;
  background-color: #2196F3;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
}

.home-button:hover {
  background-color: #1976D2;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
</style>