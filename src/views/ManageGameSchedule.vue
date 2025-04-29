<template>
    <div class="p-6">
      <h1 class="text-3xl font-bold mb-6">Manage Game Schedule</h1>
      <div class="bg-white shadow-lg rounded-xl p-6">
        <GameList />
      </div>
    </div>
  </template>
  
  <script setup>
  import GameList from '@/components/GameList.vue';
  </script>
  