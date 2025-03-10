<template>
  <div class="subject-view">
    <h1>과목 선택</h1>
    <div class="subject-buttons">
      <button 
        v-for="subject in subjects" 
        :key="subject.subject_id"
        @click="selectSubject(subject.subject_id, subject.subject_name)"
        class="subject-button"
      >
        {{ subject.subject_name }}
      </button>
    </div>
    <button @click="goBack" class="back-button">돌아가기</button>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'TeacherSubject',
  data() {
    return {
      subjects: []
    }
  },
  methods: {
    async fetchSubjects() {
      try {
        const response = await axiosInst.get('/subjects');
        this.subjects = response.data;
      } catch (error) {
        console.error('과목 목록 조회 실패:', error);
      }
    },
    selectSubject(subjectId, subjectName) {
      this.$router.push({
        name: 'teacherscore',
        query: { 
          subjectId: subjectId,
          subjectName: subjectName
        }
      });
    },
    goBack() {
      this.$router.go(-1);
    }
  },
  created() {
    this.fetchSubjects();
  }
}
</script>

<style scoped>
.subject-view {
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
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  width: 100%;
  max-width: 800px;
  margin-bottom: 30px;
}

.subject-button {
  padding: 20px;
  font-size: 1.2em;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.subject-button:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

.back-button {
  padding: 15px 30px;
  font-size: 1.1em;
  background-color: #666;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #555;
}
</style> 