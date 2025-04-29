import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import InviteCrewMember from './views/InviteCrewMember.vue'
import Admin from './views/Admin.vue'
import ManageCrew from './views/ManageCrew.vue'
import Register from './views/Register.vue'
import ScheduleCrew from './views/ScheduleCrew.vue'
import CreateGameSchedule from './views/CreateGameSchedule.vue'
import ManageGameSchedule from './views/ManageGameSchedule.vue'
import CrewList from './views/CrewList.vue'
import CrewMemberProfile from './views/CrewMemberProfile.vue'
import GameSchedule from './views/GameSchedule.vue'
import SubmitAvailability from './components/SubmitAvailability.vue'

const routes = [
  { 
    path: '/', 
    redirect: '/home'
  },
  { 
    path: '/login', 
    component: Login,
    meta: { requiresAuth: false }
  },
  { 
    path: '/register', 
    component: Register,
    meta: { requiresAuth: false }
  },
  { 
    path: '/home', 
    component: Home,
    meta: { requiresAuth: false }
  },
  { 
    path: '/crewlist', 
    component: CrewList,
    meta: { requiresAuth: true }
  },
  { 
    path: '/submit-availability', 
    component: SubmitAvailability,
    meta: { requiresAuth: true }
  },
  { 
    path: '/invite', 
    component: InviteCrewMember,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/admin', 
    component: Admin,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/manage-crew', 
    component: ManageCrew,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/schedule-crew', 
    component: ScheduleCrew,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/create-game-schedule', 
    component: CreateGameSchedule,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/manage-game-schedule', 
    component: ManageGameSchedule,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/crew-member/:id', 
    component: CrewMemberProfile,
    meta: { requiresAuth: true }
  },
  { 
    path: '/game-schedule', 
    component: GameSchedule,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('currentUser')
  const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
  const isAdmin = currentUser?.role === 'Admin'

  // If route requires auth and user is not authenticated
  if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
    next('/login')
  }
  // If route requires admin and user is not admin
  else if (to.matched.some(record => record.meta.requiresAdmin) && !isAdmin) {
    next('/home')
  }
  // If user is authenticated and trying to access login/register
  else if (isAuthenticated && (to.path === '/login' || to.path === '/register')) {
    next('/home')
  }
  else {
    next()
  }
})

export default router
