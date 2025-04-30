<template>
  <div class="submit-availability bg-white rounded-lg shadow-xl p-8">
    <h1 class="text-3xl font-bold text-left text-purple-700 mb-8">Available Shifts</h1>
    <div class="overflow-x-auto">
      <table class="min-w-full bg-white rounded mb-8">
        <thead>
          <tr class="bg-gray-100 text-gray-700 border-b">
            <th class="px-4 py-3 text-left font-semibold">Game</th>
            <th class="px-4 py-3 text-left font-semibold">Date</th>
            <th class="px-4 py-3 text-left font-semibold">Time</th>
            <th class="px-4 py-3 text-left font-semibold">Venue</th>
            <th class="px-4 py-3 text-left font-semibold">Availability</th>
            <th class="px-4 py-3 text-left font-semibold">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in availableGames" :key="game.id" class="border-b last:border-b-0">
            <td class="px-4 py-3">{{ game.game || game.opponent }}</td>
            <td class="px-4 py-3">{{ game.date }}</td>
            <td class="px-4 py-3">{{ game.time }}</td>
            <td class="px-4 py-3">{{ game.venue }}</td>
            <td class="px-4 py-3">
              <label class="inline-flex items-center mr-3">
                <input type="radio" v-model="game.isAvailable" :value="true" class="form-radio h-4 w-4 text-purple-600" />
                <span class="ml-1 text-gray-700 text-sm">Available</span>
              </label>
              <label class="inline-flex items-center">
                <input type="radio" v-model="game.isAvailable" :value="false" class="form-radio h-4 w-4 text-purple-600" />
                <span class="ml-1 text-gray-700 text-sm">Not Available</span>
              </label>
            </td>
            <td class="px-4 py-3">
              <button 
                :disabled="game.isAvailable === null"
                @click="submitAvailability(game)"
                class="bg-purple-600 text-white px-4 py-1 rounded font-semibold text-sm hover:bg-purple-700 disabled:opacity-50"
              >
                Submit
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="success" class="mt-4 text-green-600 text-center">{{ success }}</div>
  </div>
</template>

<script>
export default {
  name: "SubmitAvailability",
  data() {
    return {
      availableGames: [],
      submittedAvailabilities: [],
      success: ''
    };
  },
  created() {
    // Load published games from localStorage
    const published = JSON.parse(localStorage.getItem('publishedSchedules') || '[]')
    // Load submitted availabilities
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
    this.submittedAvailabilities = JSON.parse(localStorage.getItem('submittedAvailabilities') || '[]')
    // Only show games not already submitted for
    this.availableGames = published.filter(game => !this.submittedAvailabilities.some(a => a.shiftId === game.id && a.userId === currentUser.id)).map(game => ({ ...game, isAvailable: null }))
  },
  methods: {
    submitAvailability(game) {
      const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
      if (game.isAvailable === null) return
      // Save availability
      this.submittedAvailabilities.push({ shiftId: game.id, userId: currentUser.id, available: game.isAvailable, date: new Date().toISOString() })
      localStorage.setItem('submittedAvailabilities', JSON.stringify(this.submittedAvailabilities))
      this.success = 'Your availability has been saved!'
      // Remove from list
      this.availableGames = this.availableGames.filter(g => g.id !== game.id)
      setTimeout(() => (this.success = ''), 2000)
    }
  }
}
</script>

<style scoped>
.submit-availability {
  max-width: 1000px;
  margin: auto;
}

th, td {
  font-size: 1rem;
}

.form-radio {
  border-color: #d1d5db;
}

.form-radio:checked {
  border-color: #9333ea;
  background-color: #9333ea;
}
</style> 