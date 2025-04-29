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
        <input v-model="crewMember" type="text" placeholder="Crew Member Name" class="input-field" />
      </div>
  
      <button type="submit" class="btn-primary">Assign</button>
    </form>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const games = ref([
    { id: 1, sportType: 'Football', dateTime: '2025-09-01T19:00' },
    { id: 2, sportType: 'Soccer', dateTime: '2025-09-05T15:00' },
  ]);
  
  const selectedGame = ref('');
  const crewMember = ref('');
  
  function assignCrew() {
    if (!selectedGame.value || !crewMember.value) {
      alert('Please select a game and enter a crew member.');
      return;
    }
    console.log(`Assigning ${crewMember.value} to game ${selectedGame.value}`);
    alert('Crew Member Assigned!');
  }
  </script>
  