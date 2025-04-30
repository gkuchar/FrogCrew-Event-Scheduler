<template>
  <nav class="bg-purple-700">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between h-16">
        <!-- Logo/Home -->
        <div class="flex-shrink-0">
          <router-link to="/home" class="text-white font-bold text-xl">FrogCrew</router-link>
        </div>

        <!-- Navigation Links -->
        <div class="hidden md:block">
          <div class="ml-10 flex items-baseline space-x-4">
            <router-link 
              to="/home"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/home' }"
            >
              Home
            </router-link>

            <router-link 
              to="/crew-members"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/crew-members' }"
            >
              Crew Members
            </router-link>

            <router-link 
              v-if="isAdmin"
              to="/crewlist"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/crewlist' }"
            >
              Game Assignments
            </router-link>

            <router-link 
              to="/game-schedule"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/game-schedule' }"
            >
              Schedule
            </router-link>

            <router-link 
              v-if="isAdmin"
              to="/submit-availability"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/submit-availability' }"
            >
              Trade Board
            </router-link>

            <router-link 
              v-if="isAdmin"
              to="/admin"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/admin' }"
            >
              Reports
            </router-link>

            <router-link 
              v-if="isAdmin"
              to="/manage-crew"
              class="text-white hover:bg-purple-600 px-3 py-2 rounded-md text-sm font-medium"
              :class="{ 'bg-purple-800': $route.path === '/manage-crew' }"
            >
              Templates
            </router-link>
          </div>
        </div>

        <!-- Mobile menu button -->
        <div class="md:hidden">
          <button 
            @click="isOpen = !isOpen"
            class="inline-flex items-center justify-center p-2 rounded-md text-white hover:bg-purple-600 focus:outline-none"
          >
            <svg 
              class="h-6 w-6" 
              :class="{'hidden': isOpen, 'block': !isOpen }"
              fill="none" 
              viewBox="0 0 24 24" 
              stroke="currentColor"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
            </svg>
            <svg 
              class="h-6 w-6" 
              :class="{'block': isOpen, 'hidden': !isOpen }"
              fill="none" 
              viewBox="0 0 24 24" 
              stroke="currentColor"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile menu -->
    <div :class="{'block': isOpen, 'hidden': !isOpen}" class="md:hidden">
      <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3">
        <router-link 
          to="/home"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/home' }"
        >
          Home
        </router-link>

        <router-link 
          to="/crew-members"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/crew-members' }"
        >
          Crew Members
        </router-link>

        <router-link 
          v-if="isAdmin"
          to="/crewlist"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/crewlist' }"
        >
          Game Assignments
        </router-link>

        <router-link 
          to="/game-schedule"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/game-schedule' }"
        >
          Schedule
        </router-link>

        <router-link 
          v-if="isAdmin"
          to="/submit-availability"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/submit-availability' }"
        >
          Trade Board
        </router-link>

        <router-link 
          v-if="isAdmin"
          to="/admin"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/admin' }"
        >
          Reports
        </router-link>

        <router-link 
          v-if="isAdmin"
          to="/manage-crew"
          class="text-white hover:bg-purple-600 block px-3 py-2 rounded-md text-base font-medium"
          :class="{ 'bg-purple-800': $route.path === '/manage-crew' }"
        >
          Templates
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navigation',
  data() {
    return {
      isOpen: false,
      isAdmin: false
    }
  },
  created() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.isAdmin = currentUser?.role === 'Admin';
  }
}
</script> 