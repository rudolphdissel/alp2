<template>
  <div class="student-manage">
    <h1>학생 관리</h1>
    <div class="add-student-form">
      <input 
        type="text" 
        v-model="newStudentName" 
        placeholder="학생 이름을 입력하세요"
        class="student-input"
      >
      <button @click="addStudent" class="add-button">등록</button>
    </div>
    <div class="student-table">
      <table>
        <thead>
          <tr>
            <th>순번</th>
            <th>교사 이름</th>
            <th>학생 이름</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(student, index) in students" :key="student.studentId">
            <td>{{ index + 1 }}</td>
            <td>{{ student.teacher.teacherName }}</td>
            <td>{{ student.studentName }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axiosInst from '@/axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'StudentManage',
  setup() {
    const students = ref([]);
    const newStudentName = ref('');

    const fetchStudents = async () => {
      const teacherId = localStorage.getItem('teacherId');
      if (teacherId) {
        try {
          const res = await axiosInst.get(`api/students/teacher/${teacherId}`);
          students.value = res.data;
        } catch (error) {
          console.error('Error fetching students:', error);
        }
      }
    };

    const addStudent = async () => {
      const teacherId = localStorage.getItem('teacherId');
      if (teacherId) {
        try {
          const res = await axiosInst.post('api/students', {
            studentName: newStudentName.value,
            teacher: { teacherId: teacherId }
          });
          students.value.push(res.data);
          newStudentName.value = '';
        } catch (error) {
          console.error('Error adding student:', error);
        }
      }
    };

    onMounted(() => {
      fetchStudents();
    });

    return {
      students,
      newStudentName,
      addStudent
    };
  }
};
</script>

<style scoped>
.student-manage {
  text-align: center;
  padding: 20px;
}

.add-student-form {
  margin-bottom: 20px;
}

.student-input {
  padding: 10px;
  margin-right: 10px;
}

.add-button {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-button:hover {
  background-color: #45a049;
}

.student-table {
  margin-top: 20px;
}

.student-table table {
  width: 100%;
  border-collapse: collapse;
}

.student-table th, .student-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.student-table th {
  background-color: #f2f2f2;
}
</style>