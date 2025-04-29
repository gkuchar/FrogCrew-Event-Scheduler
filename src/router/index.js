import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import CrewList from '../views/CrewList.vue'
import GameSchedule from '../views/GameSchedule.vue'
import SubmitAvailability from '../views/SubmitAvailability.vue'
import Admin from '../views/Admin.vue'
import ManageCrew from '../views/ManageCrew.vue'
import Login from '../views/Login.vue'
import CrewMemberProfile from '../views/CrewMemberProfile.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/crewlist',
    name: 'CrewList',
    component: CrewList
  },
  {
    path: '/game-schedule',
    name: 'GameSchedule',
    component: GameSchedule
  },
  {
    path: '/submit-availability',
    name: 'SubmitAvailability',
    component: SubmitAvailability
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  },
  {
    path: '/manage-crew',
    name: 'ManageCrew',
    component: ManageCrew
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/crew-member/:id',
    name: 'CrewMemberProfile',
    component: CrewMemberProfile
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 