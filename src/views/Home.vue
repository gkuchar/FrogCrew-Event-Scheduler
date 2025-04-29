<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-100 to-white">
    <!-- Hero Section -->
    <div class="relative overflow-hidden">
      <img :src="heroBg" alt="FrogCrew background" class="absolute inset-0 w-full h-full object-cover object-center z-0 scale-105 blur-sm" style="min-height: 320px;" />
      <div class="absolute inset-0 bg-gradient-to-b from-purple-900/70 to-purple-700/60 z-10"></div>
      <div class="absolute inset-0 bg-purple-900 opacity-60 z-20"></div>
      <div class="relative max-w-7xl mx-auto py-24 px-4 sm:py-32 sm:px-6 lg:px-8 z-30">
        <h1 class="text-4xl font-extrabold tracking-tight text-white sm:text-5xl lg:text-6xl">
          Welcome to FrogCrew
        </h1>
        <p class="mt-6 text-xl text-purple-100 max-w-3xl">
          Your ultimate platform for managing sports crew availability and scheduling. Join our team and never miss a game!
        </p>
      </div>
    </div>

    <!-- Features Section -->
    <div class="py-12 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-1 gap-8 md:grid-cols-3">
          <!-- Feature 1 -->
          <div class="relative p-6 bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300">
            <div class="text-purple-600 mb-4">
              <svg class="h-12 w-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-900">Easy Availability</h3>
            <p class="mt-2 text-gray-600">Quickly submit your availability for upcoming games and events.</p>
          </div>

          <!-- Feature 2 -->
          <div class="relative p-6 bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300">
            <div class="text-purple-600 mb-4">
              <svg class="h-12 w-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-900">Team Management</h3>
            <p class="mt-2 text-gray-600">Efficiently manage your crew and coordinate schedules.</p>
          </div>

          <!-- Feature 3 -->
          <div class="relative p-6 bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300">
            <div class="text-purple-600 mb-4">
              <svg class="h-12 w-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-900">Smart Scheduling</h3>
            <p class="mt-2 text-gray-600">Automated scheduling based on crew availability and preferences.</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Call to Action -->
    <div class="bg-purple-700">
      <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:py-16 lg:px-8 lg:flex lg:items-center lg:justify-between">
        <h2 class="text-3xl font-extrabold tracking-tight text-white sm:text-4xl">
          <span class="block">Ready to get started?</span>
          <span class="block text-purple-200">Submit your availability today.</span>
        </h2>
        <div class="mt-8 flex lg:mt-0 lg:flex-shrink-0">
          <div class="inline-flex rounded-md shadow">
            <router-link to="/submit-availability" class="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-purple-700 bg-white hover:bg-purple-50">
              Submit Availability
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      heroBg: require('../assets/hero-bg.jpg')
    }
  }
}
</script>

<style scoped>
.bg-gradient-to-b {
  background-image: linear-gradient(to bottom, var(--tw-gradient-stops));
}
</style>
  