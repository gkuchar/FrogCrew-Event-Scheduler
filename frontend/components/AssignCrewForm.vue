<template>
    <form @submit.prevent="assignCrew" class="space-y-4">
      <div>
        <label class="block text-sm font-medium mb-1">Select Game:</label>
        <select v-model="selectedGame" class="input-field">
          <option disabled value="">Please select a game</option>
          <option v-for="game in games" :key="game.id" :value="game.id">
            {{ game.sportType }} - {{ game.dateTime }}
          </option>
        </select>
      </div>
  
      <div>
        <label class="block text-sm font-medium mb-1">Assign Crew Member:</label>
        <div class="flex gap-2 items-center">
          <select v-model="selectedCrewId" class="input-field">
            <option disabled value="">Select Crew Member</option>
            <option v-for="member in crewMembers" :key="member.id" :value="member.id">
              {{ member.firstName }} {{ member.lastName }} ({{ member.qualifiedPosition }})
            </option>
          </select>
          <button
            v-if="selectedCrew"
            type="button"
            class="px-3 py-1 bg-purple-600 text-white rounded hover:bg-purple-700 text-sm"
            @click="showDetails = true"
          >
            View Details
          </button>
        </div>
      </div>
  
      <button type="submit" class="btn-primary">Assign</button>
  
      <!-- Crew Member Details Modal -->
      <div v-if="showDetails && selectedCrew" class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-40">
        <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-md relative">
          <button @click="showDetails = false" class="absolute top-2 right-2 text-gray-400 hover:text-gray-700">&times;</button>
          <h2 class="text-xl font-bold mb-4 text-purple-700">Crew Member Details</h2>
          <div class="space-y-2">
            <div><span class="font-semibold">Name:</span> {{ selectedCrew.firstName }} {{ selectedCrew.lastName }}</div>
            <div><span class="font-semibold">Email:</span> {{ selectedCrew.email }}</div>
            <div><span class="font-semibold">Phone:</span> {{ selectedCrew.phone }}</div>
            <div><span class="font-semibold">Role:</span> {{ selectedCrew.role }}</div>
            <div><span class="font-semibold">Qualified Position:</span> {{ selectedCrew.qualifiedPosition }}</div>
          </div>
        </div>
      </div>
    </form>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  
  const games = ref([
    { id: 1, sportType: 'Football', dateTime: '2025-09-01T19:00' },
    { id: 2, sportType: 'Soccer', dateTime: '2025-09-05T15:00' },
  ]);
  
  const selectedGame = ref('');
  const selectedCrewId = ref('');
  const showDetails = ref(false);
  const crewMembers = ref([]);
  
  onMounted(() => {
    const stored = localStorage.getItem('crewMembers');
    crewMembers.value = stored ? JSON.parse(stored) : [];
  });
  
  const selectedCrew = computed(() => {
    return crewMembers.value.find(m => m.id == selectedCrewId.value);
  });
  
  function assignCrew() {
    if (!selectedGame.value || !selectedCrewId.value) {
      alert('Please select a game and a crew member.');
      return;
    }
    const crew = selectedCrew.value;
    console.log(`Assigning ${crew.firstName} ${crew.lastName} to game ${selectedGame.value}`);
    alert('Crew Member Assigned!');
  }
  </script>
  
  <style scoped>
  .input-field {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #d1d5db;
    border-radius: 0.5rem;
  }
  .btn-primary {
    background-color: #4f46e5;
    color: white;
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    font-weight: bold;
    transition: background 0.3s;
  }
  .btn-primary:hover {
    background-color: #4338ca;
  }
  </style>
  