<template>
  <div class="min-h-screen bg-gray-100 p-8">
    <div class="max-w-7xl mx-auto">
      <h1 class="text-3xl font-bold text-purple-700 mb-6">Reports Dashboard</h1>
      
      <!-- Reports Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Crew Attendance Report -->
        <div class="bg-white rounded-lg shadow-md p-6">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Crew Attendance</h2>
          <div class="space-y-4">
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Total Crew Members</span>
              <span class="font-semibold">{{ crewStats.totalMembers }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Active Members</span>
              <span class="font-semibold text-green-600">{{ crewStats.activeMembers }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Inactive Members</span>
              <span class="font-semibold text-red-600">{{ crewStats.inactiveMembers }}</span>
            </div>
          </div>
        </div>

        <!-- Game Coverage Report -->
        <div class="bg-white rounded-lg shadow-md p-6">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Game Coverage</h2>
          <div class="space-y-4">
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Total Games</span>
              <span class="font-semibold">{{ gameStats.totalGames }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Upcoming Games</span>
              <span class="font-semibold text-blue-600">{{ gameStats.upcomingGames }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Completed Games</span>
              <span class="font-semibold text-green-600">{{ gameStats.completedGames }}</span>
            </div>
          </div>
        </div>

        <!-- Position Coverage Report -->
        <div class="bg-white rounded-lg shadow-md p-6">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Position Coverage</h2>
          <div class="space-y-4">
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Total Positions</span>
              <span class="font-semibold">{{ positionStats.totalPositions }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Filled Positions</span>
              <span class="font-semibold text-green-600">{{ positionStats.filledPositions }}</span>
            </div>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">Open Positions</span>
              <span class="font-semibold text-red-600">{{ positionStats.openPositions }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Detailed Reports -->
      <div class="mt-8">
        <h2 class="text-2xl font-bold text-gray-800 mb-4">Detailed Reports</h2>
        <div class="bg-white rounded-lg shadow-md p-6">
          <div class="flex space-x-4 mb-6">
            <button 
              v-for="report in reports" 
              :key="report.id"
              @click="selectedReport = report.id"
              class="px-4 py-2 rounded-md"
              :class="selectedReport === report.id ? 'bg-purple-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'"
            >
              {{ report.name }}
            </button>
          </div>

          <!-- Report Content -->
          <div v-if="selectedReport === 'attendance'" class="space-y-4">
            <h3 class="text-lg font-semibold">Attendance Report</h3>
            <div class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Games</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Attendance Rate</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="member in attendanceData" :key="member.id">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ member.name }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ member.totalGames }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ member.attendanceRate }}%</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Admin",
  data() {
    return {
      crewStats: {
        totalMembers: 25,
        activeMembers: 22,
        inactiveMembers: 3
      },
      gameStats: {
        totalGames: 45,
        upcomingGames: 12,
        completedGames: 33
      },
      positionStats: {
        totalPositions: 120,
        filledPositions: 110,
        openPositions: 10
      },
      reports: [
        { id: 'attendance', name: 'Attendance' },
        { id: 'coverage', name: 'Coverage' },
        { id: 'performance', name: 'Performance' }
      ],
      selectedReport: 'attendance',
      attendanceData: [
        { id: 1, name: 'John Smith', totalGames: 15, attendanceRate: 100 },
        { id: 2, name: 'Sarah Johnson', totalGames: 12, attendanceRate: 95 },
        { id: 3, name: 'Mike Brown', totalGames: 10, attendanceRate: 90 }
      ]
    }
  }
}
</script>

<style scoped>
.admin-home {
  padding: 20px;
}
</style>
