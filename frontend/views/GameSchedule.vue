<template>
  <div class="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
    <!-- Header -->
    <div class="max-w-7xl mx-auto">
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-purple-700">General Game Schedule</h1>
        <p class="mt-2 text-gray-600">View upcoming games and available positions</p>
      </div>

      <!-- Admin: Create Game Schedule Button -->
      <div v-if="isAdmin" class="mb-6 flex justify-center">
        <button @click="showCreateForm = !showCreateForm" class="bg-purple-600 text-white px-6 py-2 rounded font-bold hover:bg-purple-700 shadow">
          {{ showCreateForm ? 'Cancel' : 'Create Game Schedule' }}
        </button>
      </div>

      <!-- Admin: Create Game Schedule Form -->
      <div v-if="isAdmin && showCreateForm" class="mb-8 bg-white p-6 rounded shadow-md max-w-2xl mx-auto">
        <h2 class="text-xl font-bold mb-4">Create New Game Schedule</h2>
        <form @submit.prevent="submitSchedule">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Game</label>
            <input v-model="newSchedule.game" type="text" required class="w-full border rounded px-3 py-2" />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Date</label>
            <input v-model="newSchedule.date" type="date" required class="w-full border rounded px-3 py-2" />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Time</label>
            <input v-model="newSchedule.time" type="time" required class="w-full border rounded px-3 py-2" />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Venue</label>
            <input v-model="newSchedule.venue" type="text" required class="w-full border rounded px-3 py-2" />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Details</label>
            <textarea v-model="newSchedule.details" class="w-full border rounded px-3 py-2" rows="2"></textarea>
          </div>
          <div class="flex gap-4">
            <button type="submit" class="bg-purple-600 text-white px-6 py-2 rounded font-bold hover:bg-purple-700">Save as Draft</button>
            <button type="button" @click="publishSchedule" class="bg-green-600 text-white px-6 py-2 rounded font-bold hover:bg-green-700">Submit Schedule</button>
          </div>
          <div v-if="formError" class="mt-2 text-red-600">{{ formError }}</div>
          <div v-if="formSuccess" class="mt-2 text-green-600">{{ formSuccess }}</div>
        </form>
      </div>

      <!-- Admin: Draft Schedules -->
      <div v-if="isAdmin && draftSchedules.length" class="mb-8 max-w-2xl mx-auto">
        <h2 class="text-lg font-bold mb-2">Draft Schedules</h2>
        <!-- eslint-disable-next-line vue/no-unused-vars -->
        <div v-for="draft in draftSchedules" :key="draft.id" class="bg-yellow-50 border-l-4 border-yellow-400 p-4 mb-2 rounded">
          <div class="flex justify-between items-center">
            <div>
              <strong>{{ draft.game }}</strong> - {{ draft.date }} {{ draft.time }} at {{ draft.venue }}
              <div class="text-xs text-gray-600">{{ draft.details }}</div>
            </div>
            <button @click="editDraft(draft.id)" class="ml-4 text-purple-700 hover:underline">Edit</button>
          </div>
        </div>
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
          v-for="game in filteredGames" :key="game.id"
          class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow cursor-pointer relative"
          @click="openGameDetails(game)"
        >
          <!-- Delete button for admin -->
          <div class="flex justify-between items-start mb-4">
            <div>
              <h3 class="text-lg font-semibold text-gray-900">{{ game.opponent }}</h3>
              <p class="text-sm text-gray-500">{{ game.venue }}</p>
            </div>
            <div class="flex flex-wrap items-center gap-1 max-w-full">
              <span class="px-2 py-1 text-xs font-medium rounded-full"
                :class="{
                  'bg-green-100 text-green-800': game.hasOpenPositions,
                  'bg-gray-100 text-gray-800': !game.hasOpenPositions
                }"
              >
                {{ game.hasOpenPositions ? 'Open Positions' : 'Fully Staffed' }}
              </span>
              <button v-if="isAdmin" @click.stop.prevent="editGame(game)" class="flex items-center border border-purple-600 text-purple-600 px-2 py-1 rounded text-xs font-bold hover:bg-purple-50 hover:text-purple-700 transition">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                </svg>
                Edit
              </button>
              <button v-if="isAdmin" @click.stop.prevent="confirmDeleteSchedule(game.id)" class="flex items-center border border-red-600 text-red-600 px-2 py-1 rounded text-xs font-bold hover:bg-red-50 hover:text-white hover:bg-red-600 transition h-7 min-w-[60px] justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>
                Delete
              </button>
            </div>
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
                  <div class="flex gap-2 items-center">
                    <a
                      :href="'mailto:' + crew.email"
                      class="text-purple-600 hover:text-purple-700 text-sm"
                    >
                      Contact
                    </a>
                    <button
                      class="px-2 py-1 bg-purple-600 text-white rounded text-xs hover:bg-purple-700"
                      @click="showCrewDetails(crew)"
                    >
                      View Details
                    </button>
                  </div>
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

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteConfirm" class="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
      <div class="bg-white rounded shadow-lg flex flex-col items-center justify-center w-[350px] h-[350px] mx-auto">
        <h2 class="text-base font-bold mb-2 text-center">Confirm Delete</h2>
        <p class="text-xs mb-4 text-center">Are you sure you want to delete this schedule?</p>
        <div class="flex justify-center gap-2 mt-2">
          <button @click="cancelDeleteSchedule" class="px-2 py-1 rounded bg-gray-200 hover:bg-gray-300 text-xs">Cancel</button>
          <button @click="actuallyDeleteSchedule" class="flex items-center border border-red-600 text-red-600 px-2 py-1 rounded text-xs font-bold hover:bg-red-50 hover:text-white hover:bg-red-600 transition shadow">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>
            Delete
          </button>
        </div>
      </div>
    </div>

    <!-- Crew Member Details Modal -->
    <div v-if="crewDetailsModal" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-40">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-md relative">
        <button @click="crewDetailsModal = false" class="absolute top-2 right-2 text-gray-400 hover:text-gray-700">&times;</button>
        <h2 class="text-xl font-bold mb-4 text-purple-700">Crew Member Details</h2>
        <div v-if="crewDetails">
          <div class="space-y-2">
            <div><span class="font-semibold">Name:</span> {{ crewDetails.firstName || crewDetails.name }} {{ crewDetails.lastName || '' }}</div>
            <div><span class="font-semibold">Email:</span> {{ crewDetails.email }}</div>
            <div><span class="font-semibold">Phone:</span> {{ crewDetails.phone || 'N/A' }}</div>
            <div><span class="font-semibold">Role:</span> {{ crewDetails.role || 'N/A' }}</div>
            <div><span class="font-semibold">Qualified Position:</span> {{ crewDetails.qualifiedPosition || crewDetails.position || 'N/A' }}</div>
          </div>
        </div>
        <div v-else>
          <p class="text-gray-500">No additional details found for this crew member.</p>
        </div>
      </div>
    </div>

    <!-- Edit Game Form Modal -->
    <div v-if="editingGame" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4 z-50">
      <div class="bg-white rounded-lg shadow-xl max-w-2xl w-full">
        <div class="p-6">
          <div class="flex justify-between items-center mb-4">
            <h2 class="text-xl font-bold">Edit Game Schedule</h2>
            <button @click="editingGame = null" class="text-gray-400 hover:text-gray-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
          <form @submit.prevent="updateGame">
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Opponent (Game Title)</label>
              <input v-model="editingGame.opponent" type="text" required class="w-full border rounded px-3 py-2" />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Sport Type</label>
              <input v-model="editingGame.sportType" type="text" required class="w-full border rounded px-3 py-2" />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Date</label>
              <input v-model="editingGame.date" type="date" required class="w-full border rounded px-3 py-2" />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Time</label>
              <input v-model="editingGame.time" type="time" required class="w-full border rounded px-3 py-2" />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Venue</label>
              <input v-model="editingGame.venue" type="text" required class="w-full border rounded px-3 py-2" />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-1">Required Positions</label>
              <input v-model="editingGame.requiredPositions" type="text" placeholder="ex: Camera, Replay" class="w-full border rounded px-3 py-2" />
            </div>
            <div class="flex justify-end gap-4">
              <button type="button" @click="editingGame = null" class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50">
                Cancel
              </button>
              <button type="submit" class="px-4 py-2 bg-purple-600 text-white rounded-md text-sm font-medium hover:bg-purple-700">
                Save Changes
              </button>
            </div>
          </form>
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
      games: [],
      showCreateForm: false,
      newSchedule: { game: '', date: '', time: '', venue: '', details: '' },
      formError: '',
      formSuccess: '',
      draftSchedules: [],
      isAdmin: false,
      publishedSchedules: [],
      showDeleteConfirm: false,
      scheduleToDelete: null,
      crewDetailsModal: false,
      crewDetails: null,
      editingGame: null,
    }
  },
  computed: {
    filteredGames() {
      let filtered = [...this.games]
      
      // Apply search filter
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        filtered = filtered.filter(game => {
          // Safely check if properties exist before accessing them
          const opponent = game?.opponent || ''
          const venue = game?.venue || ''
          const positions = game?.requiredPositions || []
          
          return (
            opponent.toLowerCase().includes(query) ||
            venue.toLowerCase().includes(query) ||
            positions.some(pos => pos && pos.toLowerCase().includes(query))
          )
        })
      }

      // Apply sorting
      filtered.sort((a, b) => {
        switch (this.sortBy) {
          case 'date':
            return new Date(b.date) - new Date(a.date)
          case 'opponent':
            return (a.opponent || '').localeCompare(b.opponent || '')
          case 'venue':
            return (a.venue || '').localeCompare(b.venue || '')
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
    },
    submitSchedule() {
      // Validate
      if (!this.newSchedule.game || !this.newSchedule.date || !this.newSchedule.time || !this.newSchedule.venue) {
        this.formError = 'Please fill in all required fields.'
        this.formSuccess = ''
        return
      }
      this.formError = ''
      // Save as draft
      const draft = { ...this.newSchedule, id: Date.now() }
      this.draftSchedules.push(draft)
      localStorage.setItem('draftSchedules', JSON.stringify(this.draftSchedules))
      this.formSuccess = 'Schedule saved as draft! You can edit it before publishing.'
      // Reset form
      this.newSchedule = { game: '', date: '', time: '', venue: '', details: '' }
    },
    editDraft(id) {
      this.newSchedule = this.draftSchedules.find(d => d.id === id)
      this.showCreateForm = true
      // Remove from drafts
      this.draftSchedules = this.draftSchedules.filter(d => d.id !== id)
      localStorage.setItem('draftSchedules', JSON.stringify(this.draftSchedules))
    },
    publishSchedule() {
      // Validate
      if (!this.newSchedule.game || !this.newSchedule.date || !this.newSchedule.time || !this.newSchedule.venue) {
        this.formError = 'Please fill in all required fields.'
        this.formSuccess = ''
        return
      }
      this.formError = ''
      // Save as published schedule
      const published = { ...this.newSchedule, id: Date.now(), published: true }
      this.publishedSchedules.push(published)
      localStorage.setItem('publishedSchedules', JSON.stringify(this.publishedSchedules))
      this.formSuccess = 'Schedule submitted and published!'
      // Add to games list so it appears immediately
      this.games.unshift(published)
      // Reset form
      this.newSchedule = { game: '', date: '', time: '', venue: '', details: '' }
    },
    confirmDeleteSchedule(id) {
      this.showDeleteConfirm = true;
      this.scheduleToDelete = id;
    },
    actuallyDeleteSchedule() {
      const id = this.scheduleToDelete;
      this.games = this.games.filter(g => g.id !== id)
      this.publishedSchedules = this.publishedSchedules.filter(g => g.id !== id)
      localStorage.setItem('publishedSchedules', JSON.stringify(this.publishedSchedules))
      this.showDeleteConfirm = false;
      this.scheduleToDelete = null;
    },
    cancelDeleteSchedule() {
      this.showDeleteConfirm = false;
      this.scheduleToDelete = null;
    },
    showCrewDetails(crew) {
      // Try to find more details in localStorage
      const crewMembers = JSON.parse(localStorage.getItem('crewMembers') || '[]');
      const found = crewMembers.find(m => m.id == crew.id || m.email === crew.email || (m.firstName + ' ' + m.lastName) === crew.name);
      this.crewDetails = found || crew;
      this.crewDetailsModal = true;
    },
    editGame(game) {
      this.editingGame = { ...game };
      // Convert requiredPositions array to comma-separated string for editing
      if (Array.isArray(this.editingGame.requiredPositions)) {
        this.editingGame.requiredPositions = this.editingGame.requiredPositions.join(', ');
      } else if (typeof this.editingGame.requiredPositions === 'string') {
        // do nothing, already a string
      } else {
        this.editingGame.requiredPositions = '';
      }
    },
    updateGame() {
      if (!this.editingGame) return;
      // Convert requiredPositions string back to array, handle undefined/null/empty
      let reqPos = this.editingGame.requiredPositions;
      let requiredPositionsArr = [];
      if (typeof reqPos === 'string' && reqPos.trim() !== '') {
        requiredPositionsArr = reqPos.split(',').map(pos => pos.trim()).filter(Boolean);
      }
      const updatedGame = {
        ...this.editingGame,
        requiredPositions: requiredPositionsArr
      };
      // Update the game in the games array
      const index = this.games.findIndex(g => g.id === updatedGame.id);
      if (index !== -1) {
        this.games[index] = updatedGame;
      }
      // Update localStorage
      localStorage.setItem('games', JSON.stringify(this.games));
      // Close the edit modal
      this.editingGame = null;
      // Show success message
      alert('Game schedule updated successfully!');
    },
  },
  async created() {
    await this.loadGameSchedule()
    // Set isAdmin from localStorage
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
    this.isAdmin = currentUser?.role === 'Admin'
    // Load drafts
    this.draftSchedules = JSON.parse(localStorage.getItem('draftSchedules') || '[]')
    this.publishedSchedules = JSON.parse(localStorage.getItem('publishedSchedules') || '[]')
    // Add published schedules to games list
    this.games = [...this.publishedSchedules, ...this.games]
  }
}
</script>

<style scoped>
.bg-gradient-to-b {
  background-image: linear-gradient(to bottom, var(--tw-gradient-stops));
}
</style> 