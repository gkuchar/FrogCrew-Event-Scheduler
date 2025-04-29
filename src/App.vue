<template>
  <div id="app" class="min-h-screen flex bg-gray-100">
    <!-- Sidebar -->
    <aside class="w-64 bg-white shadow-lg flex flex-col items-center py-6 min-h-screen">
      <img src="./assets/logo.png" alt="FrogCrew Logo" class="h-20 w-20 object-contain mb-4" />
      <nav class="flex flex-col gap-3 w-full px-4">
        <router-link to="/home" class="sidebar-link">Homepage</router-link>
        <router-link v-if="isAuthenticated" to="/game-schedule" class="sidebar-link">Schedule</router-link>
        <router-link v-if="isAuthenticated && isAdmin" to="/crewlist" class="sidebar-link">Crew Members</router-link>
        <router-link v-if="isAuthenticated && isAdmin" to="/admin" class="sidebar-link">Reports</router-link>
        <router-link v-if="isAuthenticated" to="/submit-availability" class="sidebar-link">Trade Board</router-link>
        <router-link v-if="isAuthenticated && isAdmin" to="/manage-crew" class="sidebar-link">Templates</router-link>
      </nav>
      <div class="mt-auto w-full px-4">
        <button v-if="isAuthenticated" @click="handleLogout" class="w-full py-2 mt-4 bg-purple-700 text-white rounded hover:bg-purple-800">Logout</button>
        <router-link v-else to="/login" class="w-full block py-2 mt-4 text-center bg-purple-700 text-white rounded hover:bg-purple-800">Login</router-link>
      </div>
    </aside>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col min-h-screen">
      <header class="w-full bg-purple-700 text-white py-6 px-8 shadow flex items-center">
        <h1 class="text-2xl font-bold">{{ pageTitle }}</h1>
      </header>
      <main class="flex-1 w-full max-w-5xl mx-auto px-6 py-8">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isAuthenticated: false,
      isAdmin: false
    }
  },
  computed: {
    pageTitle() {
      // Map route names to titles
      const map = {
        Home: 'Homepage',
        CrewList: 'Crew Members',
        GameSchedule: 'Games List',
        SubmitAvailability: 'Trade Board',
        Admin: 'Reports',
        ManageCrew: 'Templates',
        Login: 'Login'
      }
      return map[this.$route.name] || ''
    }
  },
  created() {
    this.checkAuth()
  },
  methods: {
    checkAuth() {
      const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
      this.isAuthenticated = !!currentUser && !!currentUser.id
      this.isAdmin = currentUser?.role === 'Admin'
    },
    handleLogout() {
      localStorage.removeItem('currentUser')
      this.isAuthenticated = false
      this.isAdmin = false
      this.$router.push('/login')
    }
  },
  watch: {
    $route() {
      this.checkAuth()
    }
  }
}
</script>

<style>
@tailwind base;
@tailwind components;
@tailwind utilities;

.sidebar-link {
  @apply text-purple-700 hover:text-purple-900 py-2 px-3 rounded transition font-medium;
}
.router-link-active {
  @apply font-bold bg-purple-100;
}
</style>
