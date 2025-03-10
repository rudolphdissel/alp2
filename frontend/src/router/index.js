import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import StudentManage from '../views/StudentManage.vue'
import TeacherLogin from '../views/TeacherLogin.vue'
import TeacherMenu from '../views/TeacherMenu.vue'
import StudentMenu from '@/views/StudentMenu.vue'
import StudentLogin from '@/views/StudentLogin.vue'
import Unit from '@/views/Unit.vue'
import Quiz from '@/views/Quiz.vue'
import TeacherScore from '../views/TeacherScore.vue'
import TeacherSubject from '../views/TeacherSubject.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/students',
      name: 'students',
      component: StudentManage
    },
    {
      path: '/teacherlogin',
      name: 'teacherlogin',
      component: TeacherLogin
    },
    {
      path: '/teachermenu',
      name: 'teachermenu',
      component: TeacherMenu
    },
    {
      path: '/studentlogin',
      name: 'studentlogin',
      component: StudentLogin
    },
    {
      path: '/studentmenu',
      name: 'studentmenu',
      component: StudentMenu
    },
    {
      path: '/unit',
      name: 'unit',
      component: Unit
    },
    {
      path: '/quiz',
      name: 'quiz',
      component: Quiz
    },
    {
      path: '/teachersubject',
      name: 'teachersubject',
      component: TeacherSubject
    },
    {
      path: '/teacherscore',
      name: 'teacherscore',
      component: TeacherScore
    }
  ]
})

export default router

//path는 주소창에 입력하는 경로
//name은 라우터의 이름
//component는 해당 경로에 매핑될 컴포넌트