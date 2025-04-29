<template>
  <div class="max-w-md mx-auto mt-10 p-6 bg-white rounded-2xl shadow-lg">
    <h2 class="text-2xl font-bold text-center text-green-700 mb-6">
      Submit Availability
    </h2>
    <form @submit.prevent="handleSubmit" class="flex flex-col gap-4">
      <div>
        <label class="block text-sm font-semibold mb-1">Game ID</label>
        <input
          v-model="gameId"
          type="text"
          class="w-full border border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
          placeholder="Enter Game ID"
          required
        />
      </div>

      <div>
        <label class="block text-sm font-semibold mb-1">Availability</label>
        <select
          v-model="isOpen"
          class="w-full border border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
        >
          <option :value="true">Available</option>
          <option :value="false">Not Available</option>
        </select>
      </div>

      <div>
        <label class="block text-sm font-semibold mb-1">Comment</label>
        <textarea
          v-model="comment"
          class="w-full border border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
          placeholder="Optional comments..."
        ></textarea>
      </div>

      <button
        type="submit"
        class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200"
      >
        Submit
      </button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AvailabilityForm',
  data() {
    return {
      gameId: '',
      isOpen: true,
      comment: '',
    };
  },
  methods: {
    handleSubmit() {
      const formData = {
        gameId: this.gameId,
        isOpen: this.isOpen,
        comment: this.comment,
      };
      console.log('Submitted availability:', formData);
      // TODO: send this data to your backend later!
    },
  },
};
</script>

<style scoped>
/* Add any extra styling if needed */
</style>
