<template>
  <div class="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
    <!-- Header -->
    <div class="max-w-7xl mx-auto">
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-purple-700">General Game Schedule</h1>
        <p class="mt-2 text-gray-600">View upcoming games and available positions</p>
      </div>

      <!-- Notifications -->
      <div v-if="hasNewScheduleUpdate" class="mb-4 bg-purple-100 border-l-4 border-purple-500 text-purple-700 p-4 rounded">
        <div class="flex items-center">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <p>New schedule updates available!</p>
          <button @click="dismissNotification" class="ml-auto text-purple-600 hover:text-purple-800">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>

      <!-- Filter & Sort Bar -->
      <div class="bg-white rounded-lg shadow-md p-4 mb-6">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <!-- Search Input -->
          <div class="md:col-span-2">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Search by opponent, location, or keyword..."
              class="w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            />
          </div>

          <!-- Sort Dropdown -->
          <div>
            <select
              v-model="sortBy"
              class="w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            >
              <option value="date">Date (Newest to Oldest)</option>
              <option value="opponent">Opponent (A-Z)</option>
              <option value="venue">Venue (A-Z)</option>
            </select>
          </div>
        </div>
      </div>

      <!-- Game List -->
      <div v-if="filteredGames.length > 0" class="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <div
          v-for="game in filteredGames"
          :key="game.id"
          class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow cursor-pointer"
          @click="openGameDetails(game)"
        >
          <div class="flex justify-between items-start mb-4">
            <div>
              <h3 class="text-lg font-semibold text-gray-900">{{ game.opponent }}</h3>
              <p class="text-sm text-gray-500">{{ game.venue }}</p>
            </div>
            <span class="px-2 py-1 text-xs font-medium rounded-full"
              :class="{
                'bg-green-100 text-green-800': game.hasOpenPositions,
                'bg-gray-100 text-gray-800': !game.hasOpenPositions
              }"
            >
              {{ game.hasOpenPositions ? 'Open Positions' : 'Fully Staffed' }}
            </span>
          </div>

          <div class="space-y-2">
            <div class="flex items-center text-sm text-gray-600">
              <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              {{ formatDate(game.date) }}
            </div>
            <div class="flex items-center text-sm text-gray-600">
              <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              {{ formatTime(game.time) }}
            </div>
          </div>

          <div class="mt-4">
            <h4 class="text-sm font-medium text-gray-700 mb-2">Required Positions:</h4>
            <div class="flex flex-wrap gap-2">
              <span
                v-for="position in game.requiredPositions"
                :key="position"
                class="px-2 py-1 text-xs bg-purple-100 text-purple-800 rounded-full"
              >
                {{ position }}
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
        </svg>
        <h3 class="mt-2 text-sm font-medium text-gray-900">No upcoming games available at this time</h3>
        <p class="mt-1 text-sm text-gray-500">Check back later for new game schedules.</p>
        <div class="mt-6">
          <router-link
            to="/"
            class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Return to Dashboard
          </router-link>
        </div>
      </div>
    </div>

    <!-- Game Detail Modal -->
    <div v-if="selectedGame" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg shadow-xl max-w-2xl w-full">
        <div class="p-6">
          <!-- Modal Header -->
          <div class="flex justify-between items-start mb-4">
            <div>
              <h3 class="text-xl font-semibold text-gray-900">{{ selectedGame.opponent }}</h3>
              <p class="text-sm text-gray-500">{{ selectedGame.venue }}</p>
            </div>
            <button
              @click="selectedGame = null"
              class="text-gray-400 hover:text-gray-500"
            >
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          <!-- Game Details -->
          <div class="space-y-6">
            <!-- Date and Time -->
            <div class="grid grid-cols-2 gap-4">
              <div>
                <h4 class="text-sm font-medium text-gray-500">Game Date</h4>
                <p class="mt-1 text-sm text-gray-900">{{ formatDate(selectedGame.date) }}</p>
              </div>
              <div>
                <h4 class="text-sm font-medium text-gray-500">Game Time</h4>
                <p class="mt-1 text-sm text-gray-900">{{ formatTime(selectedGame.time) }}</p>
              </div>
            </div>

            <!-- Required Positions -->
            <div>
              <h4 class="text-sm font-medium text-gray-500 mb-2">Required Positions</h4>
              <div class="flex flex-wrap gap-2">
                <span
                  v-for="position in selectedGame.requiredPositions"
                  :key="position"
                  class="px-2 py-1 text-xs bg-purple-100 text-purple-800 rounded-full"
                >
                  {{ position }}
                </span>
              </div>
            </div>

            <!-- Assigned Crew -->
            <div>
              <h4 class="text-sm font-medium text-gray-500 mb-2">Assigned Crew</h4>
              <div class="space-y-2">
                <div
                  v-for="crew in selectedGame.assignedCrew"
                  :key="crew.id"
                  class="flex items-center justify-between p-2 bg-gray-50 rounded-md"
                >
                  <div>
                    <p class="text-sm font-medium text-gray-900">{{ crew.name }}</p>
                    <p class="text-xs text-gray-500">{{ crew.position }}</p>
                  </div>
                  <a
                    :href="'mailto:' + crew.email"
                    class="text-purple-600 hover:text-purple-700 text-sm"
                  >
                    Contact
                  </a>
                </div>
              </div>
            </div>

            <!-- Reporting Time -->
            <div>
              <h4 class="text-sm font-medium text-gray-500">Reporting Time</h4>
              <p class="mt-1 text-sm text-gray-900">{{ formatTime(selectedGame.reportingTime) }}</p>
            </div>

            <!-- Additional Information -->
            <div>
              <h4 class="text-sm font-medium text-gray-500 mb-2">Additional Information</h4>
              <p class="text-sm text-gray-900">{{ selectedGame.additionalInfo }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { gameScheduleService } from '../services/gameScheduleService'

export default {
  name: 'GameSchedule',
  data() {
    return {
      searchQuery: '',
      sortBy: 'date',
      selectedGame: null,
      hasNewScheduleUpdate: false,
      games: []
    }
  },
  computed: {
    filteredGames() {
      let filtered = [...this.games]
      
      // Apply search filter
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        filtered = filtered.filter(game => 
          game.opponent.toLowerCase().includes(query) ||
          game.venue.toLowerCase().includes(query) ||
          game.requiredPositions.some(pos => pos.toLowerCase().includes(query))
        )
      }

      // Apply sorting
      filtered.sort((a, b) => {
        switch (this.sortBy) {
          case 'date':
            return new Date(b.date) - new Date(a.date)
          case 'opponent':
            return a.opponent.localeCompare(b.opponent)
          case 'venue':
            return a.venue.localeCompare(b.venue)
          default:
            return 0
        }
      })

      return filtered
    }
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString('en-US', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    },
    formatTime(time) {
      return new Date(`2000-01-01T${time}`).toLocaleTimeString('en-US', {
        hour: 'numeric',
        minute: '2-digit',
        hour12: true
      })
    },
    openGameDetails(game) {
      this.selectedGame = game
    },
    dismissNotification() {
      this.hasNewScheduleUpdate = false
    },
    async loadGameSchedule() {
      try {
        this.games = await gameScheduleService.getGameSchedule()
        this.hasNewScheduleUpdate = await gameScheduleService.checkForUpdates()
      } catch (error) {
        console.error('Error loading game schedule:', error)
      }
    }
  },
  async created() {
    await this.loadGameSchedule()
  }
}
</script>

<style scoped>
.bg-gradient-to-b {
  background-image: linear-gradient(to bottom, var(--tw-gradient-stops));
}
</style> 