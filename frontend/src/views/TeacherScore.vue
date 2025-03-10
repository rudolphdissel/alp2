<template>
  <div class="score-view">
    <h1>{{ $route.query.subjectName }} 성적 조회</h1>
    
    <div class="filter-section">
      <select v-model="selectedUnit" class="unit-filter">
        <option value="">전체 단원</option>
        <option v-for="unit in uniqueUnits" :key="unit" :value="unit">
          {{ unit }}
        </option>
      </select>
    </div>

    <div class="content-wrapper">
      <div class="score-table">
        <table>
          <thead>
            <tr>
              <th>학생 이름</th>
              <th>단원</th>
              <th>점수</th>
              <th>성취도</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="score in filteredScores" :key="score.studentName + '-' + score.unitName">
              <td>{{ score.studentName }}</td>
              <td>{{ score.unitName }}</td>
              <td>{{ score.percentage.toFixed(1) }}%</td>
              <td>{{ getAchievementLevel(score.percentage) }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="chart-container">
        <h2>{{ selectedUnit ? selectedUnit : '전체' }} 성취도 분포</h2>
        <div class="chart-wrapper">
          <canvas ref="chartCanvas"></canvas>
        </div>
        <div class="chart-legend">
          <div v-for="(count, level) in achievementStats" :key="level" class="legend-item">
            <span class="legend-color" :style="{ backgroundColor: getChartColor(level) }"></span>
            <span>{{ level }}: {{ ((count / filteredScores.length) * 100).toFixed(1) }}%</span>
          </div>
        </div>
      </div>
    </div>

    <button @click="goBack" class="back-button">돌아가기</button>
  </div>
</template>

<script>
import axiosInst from '../axios'
import { Chart, ArcElement, Tooltip, Legend, PieController } from 'chart.js'

Chart.register(ArcElement, Tooltip, Legend, PieController)

export default {
  name: 'TeacherScore',
  data() {
    return {
      scores: [],
      selectedUnit: '',
      chart: null
    }
  },
  computed: {
    uniqueUnits() {
      return [...new Set(this.scores.map(score => score.unitName))];
    },
    filteredScores() {
      if (!this.selectedUnit) return this.scores;
      return this.scores.filter(score => score.unitName === this.selectedUnit);
    },
    achievementStats() {
      const stats = {
        '매우 우수': 0,
        '우수': 0,
        '보통': 0,
        '미흡': 0
      };
      this.filteredScores.forEach(score => {
        const level = this.getAchievementLevel(score.percentage);
        stats[level]++;
      });
      return stats;
    }
  },
  methods: {
    getAchievementLevel(percentage) {
      if (percentage >= 76) return '매우 우수';
      if (percentage >= 51) return '우수';
      if (percentage >= 21) return '보통';
      return '미흡';
    },
    getChartColor(level) {
      const colors = {
        '매우 우수': '#4CAF50',
        '우수': '#8BC34A',
        '보통': '#FFC107',
        '미흡': '#FF5722'
      };
      return colors[level];
    },
    goBack() {
      this.$router.go(-1);
    },
    async fetchScores() {
      try {
        const teacherId = localStorage.getItem('teacherId');
        const subjectId = this.$route.query.subjectId;
        const response = await axiosInst.get(`/api/student-diff/teacher/${teacherId}/subject/${subjectId}`);
        this.scores = response.data;
        this.updateChart();
      } catch (error) {
        console.error('성적 조회 실패:', error);
      }
    },
    updateChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const ctx = this.$refs.chartCanvas.getContext('2d');
      const stats = this.achievementStats;
      const total = Object.values(stats).reduce((a, b) => a + b, 0);
      
      this.chart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: Object.keys(stats),
          datasets: [{
            data: Object.values(stats),
            backgroundColor: Object.keys(stats).map(level => this.getChartColor(level)),
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              display: true,
              position: 'bottom'
            },
            tooltip: {
              enabled: true
            }
          }
        }
      });
    }
  },
  watch: {
    selectedUnit: {
      handler() {
        this.$nextTick(() => {
          this.updateChart();
        });
      }
    }
  },
  created() {
    this.fetchScores();
  },
  unmounted() {
    if (this.chart) {
      this.chart.destroy();
    }
  }
}
</script>

<style scoped>
.score-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

h1, h2 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
}

.content-wrapper {
  display: flex;
  gap: 30px;
  margin-bottom: 30px;
}

.score-table {
  flex: 1;
  overflow-x: auto;
}

.chart-container {
  flex: 1;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
}

.chart-wrapper {
  height: 300px;
  position: relative;
  margin-bottom: 20px;
}

.filter-section {
  margin-bottom: 20px;
  text-align: right;
}

.unit-filter {
  padding: 8px;
  font-size: 1em;
  border: 1px solid #ddd;
  border-radius: 4px;
  min-width: 200px;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

th, td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #4CAF50;
  color: white;
}

tr:hover {
  background-color: #f5f5f5;
}

.back-button {
  display: block;
  margin: 20px auto;
  padding: 10px 20px;
  font-size: 1.1em;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #45a049;
}

.chart-legend {
  margin-top: 20px;
  text-align: center;
}

.legend-item {
  display: inline-flex;
  align-items: center;
  margin: 0 10px 8px;
}

.legend-color {
  width: 15px;
  height: 15px;
  margin-right: 8px;
  border-radius: 3px;
}
</style> 