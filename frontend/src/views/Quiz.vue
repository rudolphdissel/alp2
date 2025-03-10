<template>
    <div class="quiz-view">
      <h1>퀴즈</h1>
      <div v-if="!isFinished" class="quiz-question">
        <p>{{ currentQuiz.problem }}</p>
        <input type="text" v-model="answer" placeholder="답안을 입력하세요" class="quiz-input">
        <button @click="checkAnswer" class="quiz-button">다음</button>
      </div>
      <div v-else class="quiz-result">
        <h2>퀴즈 완료!</h2>
        <p>점수: {{ score }}/{{ quizzes.length }}</p>
        <button @click="goToSubjectMenu" class="back-button">과목 선택으로 돌아가기</button>
      </div>
    </div>
  </template>
  
  <script>
  import axiosInst from '../axios'
  
  export default {
    name: 'Quiz',
    data() {
      return {
        quizzes: [],
        currentIndex: 0,
        answer: '',
        score: 0,
        isFinished: false
      }
    },
    computed: {
      currentQuiz() {
        return this.quizzes[this.currentIndex] || {};
      }
    },
    methods: {
      goToSubjectMenu() {
        this.$router.push('/studentmenu');
      },
      async checkAnswer() {
        const studentData = JSON.parse(localStorage.getItem('student'));
        
        if (this.answer.toLowerCase() === this.currentQuiz.right_answer.toLowerCase()) {
          try {
            await axiosInst.post('api/student-diff/increment-score', {
              student_id: studentData.studentId,
              quizset_id: this.$route.query.quizsetId
            });
            this.score++;
          } catch (error) {
            console.error('점수 업데이트 실패:', error);
          }
        }
  
        this.answer = '';
        
        if (this.currentIndex < this.quizzes.length - 1) {
          this.currentIndex++;
        } else {
          this.isFinished = true;
          try {
            // 최종 점수 조회
            const response = await axiosInst.get('api/student-diff/score', {
              params: {
                student_id: studentData.studentId,
                quizset_id: this.$route.query.quizsetId
              }
            });
            this.score = response.data.score;

            // 난이도 업데이트
            await axiosInst.post('quiz/update-difficulty', {
              student_id: studentData.studentId,
              quizset_id: this.$route.query.quizsetId,
              total_questions: this.quizzes.length,
              correct_answers: this.score
            });
          } catch (error) {
            console.error('최종 점수 조회 실패:', error);
          }
        }
      }
    },
    async created() {
      try {
        const quizsetId = this.$route.query.quizsetId;
        const unitName = this.$route.query.unitName;
        const studentData = JSON.parse(localStorage.getItem('student'));
        
        // 점수 초기화
        await axiosInst.post('api/student-diff/reset-score', {
          student_id: studentData.studentId,
          quizset_id: quizsetId
        });

        // 현재 난이도에 맞는 퀴즈 가져오기
        const response = await axiosInst.get(`/quiz/quizzes`, {
          params: {
            studentId: studentData.studentId,
            quizsetId: quizsetId,
            quizName: this.$route.query.quizName
          }
        });
        this.quizzes = response.data;
      } catch (error) {
        console.error('퀴즈 초기화에 실패했습니다:', error);
      }
    }
  }
  </script>
  
  <style scoped>
  .quiz-view {
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
  
  .quiz-question {
    display: flex;
    flex-direction: column;
    gap: 20px;
    width: 100%;
    max-width: 600px;
  }
  
  .quiz-input {
    padding: 15px;
    font-size: 1.2em;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 100%;
  }
  
  .quiz-button {
    padding: 15px 30px;
    font-size: 1.2em;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .quiz-button:hover {
    background-color: #45a049;
  }
  
  .quiz-result {
    text-align: center;
  }
  
  .quiz-result h2 {
    color: #333;
    margin-bottom: 20px;
  }
  
  .back-button {
    margin-top: 20px;
    padding: 15px 30px;
    font-size: 1.2em;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .back-button:hover {
    background-color: #45a049;
  }
  </style>