// Mock data for crew list
const mockCrewList = [
  {
    id: 1,
    sport: 'Basketball',
    opponent: 'Eagles',
    gameDate: '2024-03-15',
    gameTime: '19:00',
    positions: [
      {
        position: 'Producer',
        name: 'John Smith',
        reportTime: '18:00',
        reportLocation: 'Main Control Room'
      },
      {
        position: 'Director',
        name: 'Sarah Johnson',
        reportTime: '18:00',
        reportLocation: 'Main Control Room'
      },
      {
        position: 'Technical Director',
        name: 'Mike Brown',
        reportTime: '17:30',
        reportLocation: 'Main Control Room'
      },
      {
        position: 'Camera',
        name: 'Emily Davis',
        reportTime: '17:30',
        reportLocation: 'Camera 1 Position'
      }
    ]
  },
  {
    id: 2,
    sport: 'Football',
    opponent: 'Hawks',
    gameDate: '2024-03-20',
    gameTime: '15:00',
    positions: [
      {
        position: 'Producer',
        name: 'David Wilson',
        reportTime: '14:00',
        reportLocation: 'Main Control Room'
      },
      {
        position: 'Director',
        name: 'Lisa Anderson',
        reportTime: '14:00',
        reportLocation: 'Main Control Room'
      },
      {
        position: 'Graphics',
        name: 'Tom Clark',
        reportTime: '13:30',
        reportLocation: 'Graphics Room'
      }
    ]
  }
]

// Service methods
export const crewListService = {
  // Get all crew lists
  async getCrewLists() {
    // In a real application, this would be an API call
    return mockCrewList
  },

  // Get crew list by game ID
  async getCrewListByGameId(gameId) {
    return mockCrewList.find(list => list.id === gameId)
  },

  // Get crew list by date range
  async getCrewListsByDateRange(startDate, endDate) {
    return mockCrewList.filter(list => {
      const gameDate = new Date(list.gameDate)
      return gameDate >= new Date(startDate) && gameDate <= new Date(endDate)
    })
  },

  // Get crew list by sport
  async getCrewListsBySport(sport) {
    return mockCrewList.filter(list => list.sport.toLowerCase() === sport.toLowerCase())
  }
} 