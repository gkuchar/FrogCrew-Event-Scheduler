<template>
  <div class="trade-board bg-white rounded shadow p-8">
    <!-- Tabs -->
    <div class="flex gap-2 mb-8">
      <button
        class="px-6 py-2 font-semibold border rounded-t-md focus:outline-none font-serif text-lg"
        :class="tab === 'my' ? 'border-purple-700 border-b-0 text-purple-700 bg-white z-10' : 'border-gray-300 bg-gray-50 text-gray-500'"
        @click="tab = 'my'"
        style="margin-bottom: -1px;"
      >
        My Shifts
      </button>
      <button
        class="px-6 py-2 font-semibold border rounded-t-md focus:outline-none font-serif text-lg"
        :class="tab === 'available' ? 'border-purple-700 border-b-0 text-purple-700 bg-white z-10' : 'border-gray-300 bg-gray-50 text-gray-500'"
        @click="tab = 'available'"
        style="margin-bottom: -1px;"
      >
        Available Shifts
      </button>
    </div>

    <!-- Section Title & Search -->
    <div class="flex flex-col md:flex-row md:items-center md:justify-between mb-4">
      <h2 class="text-4xl font-bold font-serif mb-2 md:mb-0">Available Shifts</h2>
      <div class="relative w-full md:w-80">
        <input
          v-model="search"
          type="text"
          placeholder="Search games, positions..."
          class="w-full border border-gray-300 rounded px-4 py-2 focus:outline-none focus:ring-2 focus:ring-purple-200 text-lg bg-gray-50"
        />
        <span class="absolute right-3 top-3 text-gray-400">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-4.35-4.35m0 0A7.5 7.5 0 104.5 4.5a7.5 7.5 0 0012.15 12.15z" /></svg>
        </span>
      </div>
    </div>

    <!-- Table -->
    <div v-if="loading" class="text-center py-8">
      <p>Loading shifts...</p>
    </div>
    <div v-else-if="filteredShifts.length === 0" class="text-center py-8">
      <p>No shifts available at this time.</p>
    </div>
    <div v-else>
      <table class="min-w-full bg-white rounded overflow-hidden mb-8">
        <thead>
          <tr class="bg-gray-50 text-gray-700 border-b">
            <th class="px-4 py-3 text-left font-semibold">Game</th>
            <th class="px-4 py-3 text-left font-semibold">Date</th>
            <th class="px-4 py-3 text-left font-semibold">Time</th>
            <th class="px-4 py-3 text-left font-semibold">Position</th>
            <th class="px-4 py-3 text-left font-semibold">Available From</th>
            <th class="px-4 py-3 text-left font-semibold">Status</th>
            <th class="px-4 py-3 text-left font-semibold">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="shift in filteredShifts" :key="shift.id" class="border-b last:border-b-0">
            <td class="px-4 py-3">{{ shift.game }}</td>
            <td class="px-4 py-3">{{ shift.date }}</td>
            <td class="px-4 py-3">{{ shift.time }}</td>
            <td class="px-4 py-3">{{ shift.position }}</td>
            <td class="px-4 py-3">{{ shift.availableFrom }}</td>
            <td class="px-4 py-3">
              <span :class="statusClass(shift.status)">{{ statusLabel(shift.status) }}</span>
            </td>
            <td class="px-4 py-3 flex gap-2">
              <button v-if="canPickUp(shift)" @click="pickUpShift(shift)" class="bg-green-600 text-white px-5 py-1 rounded font-bold hover:bg-green-700 text-base shadow-sm">Pick Up Shift</button>
              <button v-if="canSwitch(shift)" @click="switchShift(shift)" class="bg-blue-600 text-white px-5 py-1 rounded font-bold hover:bg-blue-700 text-base shadow-sm">Switch Shift</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Information Box -->
    <div class="bg-gray-50 border rounded p-6 mt-4">
      <h3 class="text-3xl font-bold font-serif mb-2">Information</h3>
      <ul class="list-disc pl-6 text-gray-700 text-lg">
        <li>View all available shifts that need coverage</li>
        <li>Pick up shifts that best match your qualifications</li>
        <li>Trades require admin approval after pickup</li>
      </ul>
    </div>
    <div v-if="error" class="mt-4 text-red-600">{{ error }}</div>
    <div v-if="success" class="mt-4 text-green-600">{{ success }}</div>
  </div>
</template>

<script>
export default {
  name: 'TradeBoard',
  data() {
    return {
      shifts: [],
      loading: true,
      error: '',
      success: '',
      currentUser: {},
      tab: 'available',
      search: ''
    }
  },
  computed: {
    isAdmin() {
      return this.currentUser?.role === 'Admin'
    },
    filteredShifts() {
      let list = this.shifts
      if (this.tab === 'my') {
        list = list.filter(shift => shift.availableFrom === this.currentUser.name)
      }
      if (this.search) {
        const s = this.search.toLowerCase()
        list = list.filter(shift =>
          shift.game.toLowerCase().includes(s) ||
          shift.position.toLowerCase().includes(s)
        )
      }
      return list
    }
  },
  methods: {
    statusClass(status) {
      if (status === 'available') return 'bg-green-100 text-green-700 px-3 py-1 rounded font-semibold text-base border border-green-200'
      if (status === 'pending') return 'bg-yellow-100 text-yellow-700 px-3 py-1 rounded font-semibold text-base border border-yellow-200'
      if (status === 'closed') return 'bg-gray-200 text-gray-500 px-3 py-1 rounded font-semibold text-base border border-gray-300'
      return ''
    },
    statusLabel(status) {
      if (status === 'available') return 'Available'
      if (status === 'pending') return 'Pending'
      if (status === 'closed') return 'Closed'
      return status
    },
    canPickUp(shift) {
      // Only show for available shifts not posted by the user
      return shift.status === 'available' && shift.availableFrom !== this.currentUser.name
    },
    canSwitch(shift) {
      // Allow switch for all shifts that are not closed
      return shift.status !== 'closed'
    },
    pickUpShift(shift) {
      shift.status = 'pending'
      this.success = 'You have requested to pick up this shift.'
      setTimeout(() => (this.success = ''), 2000)
    },
    switchShift(shift) {
      shift.status = 'pending'
      this.success = 'You have requested to switch this shift.'
      setTimeout(() => (this.success = ''), 2000)
    },
    async loadShifts() {
      this.loading = true
      try {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
        // Mock data for demonstration
        const allShifts = [
          { id: 1, game: 'TCU vs SMU', date: '12/20/2025', time: '7:00 PM', position: 'UTILITY', availableFrom: 'Tim Daly', status: 'available' },
          { id: 2, game: 'TCU vs Colorado', date: '10/4/2025', time: '6:00 PM', position: 'VIDEO OPERATOR', availableFrom: 'Tim Daly', status: 'pending' }
        ]
        this.shifts = allShifts
      } catch (err) {
        this.error = 'Error loading shifts.'
      } finally {
        this.loading = false
      }
    }
  },
  async created() {
    await this.loadShifts()
  }
}
</script>

<style scoped>
.trade-board {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Inter', ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif;
}
</style> 