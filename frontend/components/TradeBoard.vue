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
              <td class="px-4 py-3 flex gap-2 flex-wrap">
                <button
                  v-if="shift.status === 'Available' && !isAdmin"
                  @click="pickUpShift(shift)"
                  class="bg-green-600 text-white px-4 py-1 rounded font-semibold text-sm hover:bg-green-700"
                >
                  Pick Up Shift
                </button>
                <!-- Admin actions -->
                <template v-if="isAdmin">
                  <button @click="openAssignModal(shift)" class="bg-gray-500 text-white px-3 py-1 rounded text-xs hover:bg-gray-700">Assign</button>
                  <button @click="openSwitchModal(shift)" class="bg-purple-500 text-white px-3 py-1 rounded text-xs hover:bg-purple-700">Switch</button>
                  <button @click="deleteShift(shift)" class="bg-red-600 text-white px-3 py-1 rounded text-xs hover:bg-red-700">Delete</button>
                </template>
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
      <div v-if="myShifts.length > 0" class="overflow-x-auto">
        <table class="min-w-full bg-white rounded mb-8">
          <thead>
            <tr class="bg-gray-100 text-gray-700 border-b">
              <th class="px-4 py-3 text-left font-semibold">Game</th>
              <th class="px-4 py-3 text-left font-semibold">Date</th>
              <th class="px-4 py-3 text-left font-semibold">Time</th>
              <th class="px-4 py-3 text-left font-semibold">Sport</th>
              <th class="px-4 py-3 text-left font-semibold">Position</th>
              <th class="px-4 py-3 text-left font-semibold">Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="shift in myShifts" :key="shift.id" class="border-b last:border-b-0">
              <td class="px-4 py-3">{{ shift.game }}</td>
              <td class="px-4 py-3">{{ shift.date }}</td>
              <td class="px-4 py-3">{{ shift.time }}</td>
              <td class="px-4 py-3">{{ shift.sport }}</td>
              <td class="px-4 py-3">{{ shift.position }}</td>
              <td class="px-4 py-3">
                <span v-if="shift.status === 'Available'" class="bg-green-100 text-green-700 px-2 py-1 rounded text-xs font-semibold">Available</span>
                <span v-else class="bg-yellow-100 text-yellow-700 px-2 py-1 rounded text-xs font-semibold">Pending</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else class="text-gray-500 text-center py-12">No shifts picked up yet.</div>
    </div>
    <!-- Assign/Switch Modal -->
    <div v-if="showAssignModal" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-40">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-md relative">
        <button @click="closeAssignModal" class="absolute top-2 right-2 text-gray-400 hover:text-gray-700">&times;</button>
        <h2 class="text-xl font-bold mb-4 text-purple-700">Select Crew Member</h2>
        <div class="mb-4">
          <select v-model="selectedCrewId" class="w-full border rounded px-3 py-2">
            <option disabled value="">Select Crew Member</option>
            <option v-for="member in crewMembers" :key="member.id" :value="member.id">
              {{ member.firstName }} {{ member.lastName }} ({{ member.qualifiedPosition }})
            </option>
          </select>
        </div>
        <div class="flex gap-2 justify-end">
          <button @click="assignShiftToCrew" class="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700">Assign</button>
          <button @click="closeAssignModal" class="bg-gray-300 text-gray-700 px-4 py-1 rounded hover:bg-gray-400">Cancel</button>
        </div>
      </div>
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
        {
          id: 3,
          game: 'TCU vs Baylor',
          date: '10/15/2023',
          time: '6:00 PM',
          sport: 'Basketball',
          position: 'CAMERA',
          availableFrom: 'Alex Smith',
          status: 'Available',
        },
        {
          id: 4,
          game: 'TCU vs Texas',
          date: '10/20/2023',
          time: '8:00 PM',
          sport: 'Soccer',
          position: 'AUDIO',
          availableFrom: 'Jordan Lee',
          status: 'Available',
        },
        {
          id: 5,
          game: 'TCU vs Oklahoma',
          date: '10/25/2023',
          time: '5:00 PM',
          sport: 'Baseball',
          position: 'GRAPHICS',
          availableFrom: 'Morgan Ray',
          status: 'Pending',
        },
        {
          id: 6,
          game: 'TCU vs Kansas',
          date: '11/01/2023',
          time: '3:00 PM',
          sport: 'Football',
          position: 'REPLAY',
          availableFrom: 'Chris Kim',
          status: 'Available',
        },
      ],
      isAdmin: false,
      showAssignModal: false,
      assignMode: '', // 'assign' or 'switch'
      shiftToAssign: null,
      crewMembers: [],
      selectedCrewId: '',
      myShifts: [],
      currentUser: null,
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
      alert(`You have requested to pick up the shift: ${shift.game} (${shift.position})`)
      shift.status = 'Pending'
      this.saveMyShift(shift)
      this.activeTab = 'myShifts'
    },
    saveMyShift(shift) {
      // Save picked shift for current user in localStorage
      if (!this.currentUser) return
      let allMyShifts = JSON.parse(localStorage.getItem('myShifts') || '{}')
      if (!allMyShifts[this.currentUser.id]) allMyShifts[this.currentUser.id] = []
      // Avoid duplicates
      if (!allMyShifts[this.currentUser.id].some(s => s.id === shift.id)) {
        allMyShifts[this.currentUser.id].push(shift)
        localStorage.setItem('myShifts', JSON.stringify(allMyShifts))
        this.myShifts = allMyShifts[this.currentUser.id]
      }
    },
    loadMyShifts() {
      if (!this.currentUser) return
      let allMyShifts = JSON.parse(localStorage.getItem('myShifts') || '{}')
      this.myShifts = allMyShifts[this.currentUser.id] || []
    },
    deleteShift(shift) {
      if (confirm('Are you sure you want to delete this shift?')) {
        this.shifts = this.shifts.filter(s => s.id !== shift.id)
      }
    },
    openAssignModal(shift) {
      this.assignMode = 'assign'
      this.shiftToAssign = shift
      this.showAssignModal = true
      this.selectedCrewId = ''
    },
    openSwitchModal(shift) {
      this.assignMode = 'switch'
      this.shiftToAssign = shift
      this.showAssignModal = true
      this.selectedCrewId = ''
    },
    closeAssignModal() {
      this.showAssignModal = false
      this.shiftToAssign = null
      this.selectedCrewId = ''
    },
    assignShiftToCrew() {
      if (!this.selectedCrewId) return
      const member = this.crewMembers.find(m => m.id == this.selectedCrewId)
      if (!member) return
      this.shiftToAssign.availableFrom = member.firstName + ' ' + member.lastName
      this.shiftToAssign.status = 'Pending'
      this.closeAssignModal()
    },
  },
  mounted() {
    // Set admin status
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}')
    this.isAdmin = currentUser?.role === 'Admin'
    this.currentUser = currentUser
    // Load crew members
    this.crewMembers = JSON.parse(localStorage.getItem('crewMembers') || '[]')
    this.loadMyShifts()
  },
  watch: {
    activeTab(val) {
      if (val === 'myShifts') this.loadMyShifts()
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