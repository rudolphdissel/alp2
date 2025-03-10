<template>
    <div class="unit-view">
      <h1>단원 선택</h1>
      <div class="unit-buttons">
        <router-link 
          v-for="unit in units" 
          :key="unit.unit_id"
          :to="'/quiz'"
          class="unit-button"
          @click.native.prevent="handleUnitClick(unit)"
        >
          {{ unit.unit_name }}
        </router-link>
      </div>
    </div>
  </template>
  
  <script>
  import axiosInst from '../axios'
  
  export default {
    name: 'Unit',
    data() {
      return {
        units: []
      }
    },
    methods: {
      getQuizPrefix(unitName) {
        const prefixMap = {
          '일반화학': 'nchem',
          '유기화학': 'ochem',
          '물리화학': 'pchem',
          '분석화학': 'achem'
        };
        return prefixMap[unitName] || '';
      },
      handleUnitClick(unit) {
        if (unit.quizSets && unit.quizSets.length > 0) {
          const quizPrefix = this.getQuizPrefix(unit.unit_name);
          this.$router.push({
            path: '/quiz',
            query: { 
              quizsetId: unit.quizSets[0].quizsetId,
              quizName: quizPrefix
            }
          });
        }
      }
    },
    async created() {
      try {
        const subjectId = this.$route.query.subjectId;
        console.log('Subject ID:', subjectId);
        const response = await axiosInst.get(`/units/subject/${subjectId}`);
        console.log('Units response:', response.data);
        this.units = response.data;
      } catch (error) {
        console.error('단원 목록을 가져오는데 실패했습니다:', error);
      }
    }
  }
  </script>
  
  <style scoped>
  .unit-view {
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
  
  .unit-buttons {
    display: flex;
    flex-direction: column;
    gap: 20px;
    width: 100%;
    max-width: 400px;
  }
  
  .unit-button {
    padding: 15px 30px;
    font-size: 1.2em;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    text-decoration: none;
    text-align: center;
  }
  
  .unit-button:hover {
    background-color: #45a049;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  }
  </style>