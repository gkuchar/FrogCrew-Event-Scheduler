<template>
  <div id="app" class="min-h-screen flex bg-gray-100">
    <!-- Sidebar and Main Content only if authenticated -->
    <template v-if="isAuthenticated">
      <aside class="w-64 bg-white shadow-lg flex flex-col items-center py-6 min-h-screen">
        <img src="./assets/logo.png" alt="FrogCrew Logo" class="h-20 w-20 object-contain mb-4" />
        <nav class="flex flex-col gap-3 w-full px-4">
          <!-- Navigation links for all authenticated users -->
          <router-link to="/home" class="sidebar-link">Homepage</router-link>
          <router-link to="/crewlist" class="sidebar-link">Crew Members</router-link>
          <router-link to="/game-schedule" class="sidebar-link">Schedule</router-link>
          <!-- Admin-only navigation links -->
          <template v-if="isAdmin">
            <router-link to="/admin" class="sidebar-link">Reports</router-link>
            <router-link to="/submit-availability" class="sidebar-link">Trade Board</router-link>
            <router-link to="/manage-crew" class="sidebar-link">Templates</router-link>
          </template>
        </nav>
        <div class="mt-auto w-full px-4">
          <button v-if="isAuthenticated" @click="handleLogout" class="w-full py-2 mt-4 bg-purple-700 text-white rounded hover:bg-purple-800">Logout</button>
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
    </template>
    <!-- Show only login page when not authenticated -->
    <template v-else>
      <div class="flex-1 flex flex-col min-h-screen w-full">
        <main class="flex-1 w-full max-w-5xl mx-auto px-6 py-8 flex items-center justify-center">
          <router-view></router-view>
        </main>
      </div>
    </template>
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
      console.log('Current User:', currentUser)
      this.isAuthenticated = !!currentUser && !!currentUser.id
      this.isAdmin = currentUser?.role === 'Admin'

      // Redirect if accessing admin pages without admin rights
      const adminRoutes = ['Admin', 'SubmitAvailability', 'ManageCrew']
      if (!this.isAdmin && adminRoutes.includes(this.$route.name)) {
        this.$router.push('/home')
      }
    },
    handleLogout() {
      localStorage.removeItem('currentUser')
      this.isAuthenticated = false
      this.isAdmin = false
      this.$router.push('/login')
      this.checkAuth()
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
