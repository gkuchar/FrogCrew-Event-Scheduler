// Mock data for game schedule
const mockGameSchedule = [
  {
    id: 1,
    date: '2024-03-15',
    time: '19:00',
    opponent: 'Eagles',
    venue: 'Home Stadium',
    requiredPositions: ['Camera', 'Replay', 'Audio'],
    hasOpenPositions: true,
    assignedCrew: [
      { id: 1, name: 'John Doe', position: 'Camera', email: 'john@example.com' },
      { id: 2, name: 'Jane Smith', position: 'Replay', email: 'jane@example.com' }
    ],
    reportingTime: '18:00',
    additionalInfo: 'Please arrive 15 minutes before reporting time for equipment setup.'
  },
  {
    id: 2,
    date: '2024-03-20',
    time: '15:00',
    opponent: 'Hawks',
    venue: 'Away Stadium',
    requiredPositions: ['Camera', 'Graphics', 'Technical Director'],
    hasOpenPositions: false,
    assignedCrew: [
      { id: 3, name: 'Mike Johnson', position: 'Camera', email: 'mike@example.com' },
      { id: 4, name: 'Sarah Wilson', position: 'Graphics', email: 'sarah@example.com' },
      { id: 5, name: 'Tom Brown', position: 'Technical Director', email: 'tom@example.com' }
    ],
    reportingTime: '14:00',
    additionalInfo: 'Transportation will be provided from the main office.'
  }
]

// Service methods
export const gameScheduleService = {
  // Get all games
  async getGameSchedule() {
    // In a real application, this would be an API call
    return mockGameSchedule
  },

  // Get game by ID
  async getGameById(id) {
    return mockGameSchedule.find(game => game.id === id)
  },

  // Check for schedule updates
  async checkForUpdates() {
    // In a real application, this would check the backend for updates
    return true // Mock update available
  },

  // Get crew member's assigned games
  async getCrewMemberSchedule(crewMemberId) {
    // In a real application, this would filter games for the specific crew member
    return mockGameSchedule.filter(game => 
      game.assignedCrew.some(crew => crew.id === crewMemberId)
    )
  }
} 