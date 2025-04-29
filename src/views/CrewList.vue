<template>
  <div class="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
    <div class="max-w-7xl mx-auto">
      <!-- Header -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-purple-700">Crew List</h1>
        <p class="mt-2 text-gray-600">View crew assignments for upcoming games</p>
      </div>

      <!-- Filter Controls -->
      <div class="bg-white rounded-lg shadow-md p-4 mb-6">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <!-- Sport Filter -->
          <div>
            <label for="sport" class="block text-sm font-medium text-gray-700 mb-1">Sport</label>
            <select
              id="sport"
              v-model="selectedSport"
              class="w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            >
              <option value="">All Sports</option>
              <option value="Basketball">Basketball</option>
              <option value="Football">Football</option>
              <option value="Baseball">Baseball</option>
            </select>
          </div>

          <!-- Date Range -->
          <div>
            <label for="dateRange" class="block text-sm font-medium text-gray-700 mb-1">Date Range</label>
            <select
              id="dateRange"
              v-model="selectedDateRange"
              class="w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            >
              <option value="all">All Dates</option>
              <option value="week">Next 7 Days</option>
              <option value="month">Next 30 Days</option>
            </select>
          </div>

          <!-- Search -->
          <div>
            <label for="search" class="block text-sm font-medium text-gray-700 mb-1">Search</label>
            <input
              id="search"
              v-model="searchQuery"
              type="text"
              placeholder="Search by name or position..."
              class="w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            />
          </div>
        </div>
      </div>

      <!-- Crew List -->
      <div v-if="filteredCrewLists.length > 0" class="space-y-6">
        <div
          v-for="crewList in filteredCrewLists"
          :key="crewList.id"
          class="bg-white rounded-lg shadow-md overflow-hidden"
        >
          <!-- Game Header -->
          <div class="bg-purple-700 px-4 py-3">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="text-xl font-bold text-white">{{ crewList.sport }} vs {{ crewList.opponent }}</h2>
                <p class="text-purple-100">
                  {{ formatDate(crewList.gameDate) }} at {{ formatTime(crewList.gameTime) }}
                </p>
              </div>
            </div>
          </div>

          <!-- Crew Positions -->
          <div class="p-4">
            <div class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Position
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Name
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Report Time
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Report Location
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="position in crewList.positions" :key="position.position">
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {{ position.position }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <router-link
                        v-if="position.id"
                        :to="{ name: 'CrewMemberProfile', params: { id: position.id } }"
                        class="text-purple-700 hover:underline"
                      >
                        {{ position.name }}
                      </router-link>
                      <span v-else>{{ position.name }}</span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {{ formatTime(position.reportTime) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {{ position.reportLocation }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
        </svg>
        <h3 class="mt-2 text-sm font-medium text-gray-900">No crew lists available</h3>
        <p class="mt-1 text-sm text-gray-500">Try adjusting your filters or check back later.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { crewListService } from '../services/crewListService'

export default {
  name: 'CrewList',
  data() {
    return {
      crewLists: [],
      selectedSport: '',
      selectedDateRange: 'all',
      searchQuery: ''
    }
  },
  computed: {
    filteredCrewLists() {
      let filtered = [...this.crewLists]

      // Apply sport filter
      if (this.selectedSport) {
        filtered = filtered.filter(list => list.sport === this.selectedSport)
      }

      // Apply date range filter
      if (this.selectedDateRange !== 'all') {
        const today = new Date()
        const endDate = new Date()
        
        if (this.selectedDateRange === 'week') {
          endDate.setDate(today.getDate() + 7)
        } else if (this.selectedDateRange === 'month') {
          endDate.setMonth(today.getMonth() + 1)
        }

        filtered = filtered.filter(list => {
          const gameDate = new Date(list.gameDate)
          return gameDate >= today && gameDate <= endDate
        })
      }

      // Apply search filter
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        filtered = filtered.filter(list => 
          list.positions.some(position => 
            position.name.toLowerCase().includes(query) ||
            position.position.toLowerCase().includes(query)
          )
        )
      }

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
    async loadCrewLists() {
      try {
        this.crewLists = await crewListService.getCrewLists()
      } catch (error) {
        console.error('Error loading crew lists:', error)
      }
    }
  },
  async created() {
    await this.loadCrewLists()
  }
}
</script>

<style scoped>
.bg-gradient-to-b {
  background-image: linear-gradient(to bottom, var(--tw-gradient-stops));
}
</style> 