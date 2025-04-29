<template>
    <div>
      <h2 class="text-xl font-semibold mb-4">Games Scheduled</h2>
      <ul class="space-y-2">
        <li v-for="game in games" :key="game.id" class="border p-4 rounded-lg shadow-sm">
          {{ game.sportType }} - {{ game.dateTime }} at {{ game.venue }}
        </li>
      </ul>
      <button @click="addGame" class="btn-primary mt-6">Add New Game</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const games = ref([
    { id: 1, sportType: 'Football', dateTime: '2025-09-01T19:00', venue: 'Amon G. Carter Stadium' },
    { id: 2, sportType: 'Soccer', dateTime: '2025-09-05T15:00', venue: 'Garvey-Rosenthal Stadium' },
  ]);
  
  function addGame() {
    alert('Navigate to Add New Game Form');
  }
  </script>
  