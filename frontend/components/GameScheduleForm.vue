<template>
    <form @submit.prevent="submitSchedule" class="space-y-4">
      <div>
        <label class="block text-sm font-medium mb-1">Opponent (Game Title):</label>
        <input v-model="opponent" type="text" class="input-field" required placeholder="e.g., Eagles vs Hawks" />
      </div>
  
      <div>
        <label class="block text-sm font-medium mb-1">Sport Type:</label>
        <input v-model="sportType" type="text" class="input-field" required />
      </div>
  
      <div>
        <label class="block text-sm font-medium mb-1">Game Date and Time:</label>
        <input v-model="gameDateTime" type="datetime-local" class="input-field" required />
      </div>
  
      <div>
        <label class="block text-sm font-medium mb-1">Venue:</label>
        <input v-model="venue" type="text" class="input-field" required />
      </div>
  
      <div>
        <label class="block text-sm font-medium mb-1">Required Crew Positions:</label>
        <input v-model="crewPositions" type="text" placeholder="ex: Camera, Replay" class="input-field" />
      </div>
  
      <button type="submit" class="btn-primary">Save Schedule</button>
    </form>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const opponent = ref('');
  const sportType = ref('');
  const gameDateTime = ref('');
  const venue = ref('');
  const crewPositions = ref('');
  
  function submitSchedule() {
    // Split the datetime into date and time
    const [date, time] = gameDateTime.value.split('T');
    
    // Create the game object in the correct format
    const newGame = {
      id: Date.now(),
      opponent: opponent.value,
      sportType: sportType.value,
      date: date,
      time: time,
      venue: venue.value,
      requiredPositions: crewPositions.value ? crewPositions.value.split(',').map(pos => pos.trim()) : [],
      hasOpenPositions: true
    };

    // Get existing games from localStorage
    const existingGames = JSON.parse(localStorage.getItem('games') || '[]');
    
    // Add the new game
    existingGames.push(newGame);
    
    // Save back to localStorage
    localStorage.setItem('games', JSON.stringify(existingGames));
    
    // Reset form
    opponent.value = '';
    sportType.value = '';
    gameDateTime.value = '';
    venue.value = '';
    crewPositions.value = '';
    
    alert('Schedule saved successfully!');
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
  