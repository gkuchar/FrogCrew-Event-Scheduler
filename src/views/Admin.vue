<template>
  <div class="admin-home">
    <h1 class="text-2xl font-bold mb-4">Admin Dashboard</h1>
    <p class="mb-6">Welcome, Admin! Use the navigation to manage the crew and schedules.</p>
    
    <nav class="flex space-x-4 p-4 bg-purple-600 text-white rounded-lg shadow-md mb-6">
      <RouterLink to="/create-schedule" class="hover:underline">Create Schedule</RouterLink>
      <RouterLink to="/manage-schedule" class="hover:underline">Manage Schedule</RouterLink>
    </nav>
  </div>
</template>

<script>
export default {
  name: "Admin"
}
</script>

<style scoped>
.admin-home {
  padding: 20px;
}
</style>
