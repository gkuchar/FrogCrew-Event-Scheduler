<template>
    <div class="manage-crew">
      <h1>Manage Crew Members</h1>
      <CrewList />
    </div>
  </template>
  
  <script>
  // Import the CrewList component
  import CrewList from "./CrewList.vue";
  
  export default {
    name: "ManageCrew",
    components: {
      CrewList,
    },
  };
  </script>
  
  <style scoped>
  .manage-crew {
    padding: 20px;
  }
  </style>
  