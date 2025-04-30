<template>
  <div class="trade-board bg-white rounded-lg shadow-xl p-8">
    <h1 class="text-3xl font-bold text-left text-purple-700 mb-8">Trade Board</h1>
    <div class="mb-6 border-b border-gray-200 flex gap-4">
      <button
        :class="['px-4 py-2 font-semibold', activeTab === 'myShifts' ? 'border-b-2 border-purple-700 text-purple-700' : 'text-gray-500']"
        @click="activeTab = 'myShifts'"
      >
        My Shifts
      </button>
      <button
        :class="['px-4 py-2 font-semibold', activeTab === 'availableShifts' ? 'border-b-2 border-purple-700 text-purple-700' : 'text-gray-500']"
        @click="activeTab = 'availableShifts'"
      >
        Available Shifts
      </button>
    </div>
    <div v-if="activeTab === 'availableShifts'">
      <div class="mb-4">
        <input
          v-model="search"
          type="text"
          placeholder="Search games, positions..."
          class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-purple-200"
        />
      </div>
      <div class="overflow-x-auto">
        <table class="min-w-full bg-white rounded mb-8">
          <thead>
            <tr class="bg-gray-100 text-gray-700 border-b">
              <th class="px-4 py-3 text-left font-semibold">Game</th>
              <th class="px-4 py-3 text-left font-semibold">Date</th>
              <th class="px-4 py-3 text-left font-semibold">Time</th>
              <th class="px-4 py-3 text-left font-semibold">Sport</th>
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
              <td class="px-4 py-3">{{ shift.sport }}</td>
              <td class="px-4 py-3">{{ shift.position }}</td>
              <td class="px-4 py-3">{{ shift.availableFrom }}</td>
              <td class="px-4 py-3">
                <span v-if="shift.status === 'Available'" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs font-semibold">Available</span>
                <span v-else class="bg-yellow-100 text-yellow-700 px-2 py-1 rounded text-xs font-semibold">Pending</span>
              </td>
              <td class="px-4 py-3">
                <button
                  v-if="shift.status === 'Available'"
                  @click="pickUpShift(shift)"
                  class="bg-green-600 text-white px-4 py-1 rounded font-semibold text-sm hover:bg-green-700"
                >
                  Pick Up Shift
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="bg-gray-50 p-4 rounded mt-6">
        <h2 class="text-xl font-bold mb-2">Information</h2>
        <ul class="text-gray-700 text-sm list-disc pl-5">
          <li>View all available shifts that need coverage</li>
          <li>Pick up shifts that match your qualifications</li>
          <li>Trades require admin approval after pickup</li>
        </ul>
      </div>
    </div>
    <div v-else>
      <div class="text-gray-500 text-center py-12">My Shifts tab coming soon...</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TradeBoard',
  data() {
    return {
      activeTab: 'availableShifts',
      search: '',
      shifts: [
        {
          id: 1,
          game: 'TCU vs SMU',
          date: '9/30/2023',
          time: '7:00 PM',
          sport: 'Football',
          position: 'UTILITY',
          availableFrom: 'Tina Daly',
          status: 'Available',
        },
        {
          id: 2,
          game: 'TCU vs Colorado',
          date: '10/6/2023',
          time: '2:00 PM',
          sport: 'Football',
          position: 'VIDEO OPERATOR',
          availableFrom: 'Tina Daly',
          status: 'Pending',
        },
      ],
    }
  },
  computed: {
    filteredShifts() {
      if (!this.search) return this.shifts
      const s = this.search.toLowerCase()
      return this.shifts.filter(shift =>
        shift.game.toLowerCase().includes(s) ||
        shift.position.toLowerCase().includes(s) ||
        shift.sport.toLowerCase().includes(s)
      )
    }
  },
  methods: {
    pickUpShift(shift) {
      // Placeholder for pick up logic
      alert(`You have requested to pick up the shift: ${shift.game} (${shift.position})`)
      shift.status = 'Pending'
    }
  }
}
</script>

<style scoped>
.trade-board {
  max-width: 1100px;
  margin: auto;
}

th, td {
  font-size: 1rem;
}
</style> 