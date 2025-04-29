<template>
    <div class="submit-availability bg-white rounded-lg shadow-xl p-8">
      <h1 class="text-3xl font-bold text-center text-purple-700 mb-8">Submit Your Availability</h1>
      <form @submit.prevent="submitAvailability" class="space-y-6">
        <div v-for="game in availableGames" :key="game.id" class="game-item bg-gray-50 rounded-lg p-6 hover:shadow-md transition-shadow duration-300">
          <h3 class="text-xl font-semibold text-gray-800 mb-4">{{ game.name }}</h3>
          <div class="flex items-center space-x-6">
            <label class="flex items-center space-x-2 cursor-pointer">
              <input
                type="radio"
                v-model="game.isAvailable"
                :value="true"
                name="availability"
                class="form-radio h-5 w-5 text-purple-600"
              />
              <span class="text-gray-700">Available</span>
            </label>
            <label class="flex items-center space-x-2 cursor-pointer">
              <input
                type="radio"
                v-model="game.isAvailable"
                :value="false"
                name="availability"
                class="form-radio h-5 w-5 text-purple-600"
              />
              <span class="text-gray-700">Not Available</span>
            </label>
          </div>
        </div>
        <div class="flex justify-center mt-8">
          <button 
            type="submit" 
            class="bg-purple-600 text-white py-3 px-8 rounded-lg text-lg font-semibold hover:bg-purple-700 transform hover:scale-105 transition-all duration-200 shadow-lg"
          >
            Submit Availability
          </button>
        </div>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: "SubmitAvailability",
    data() {
      return {
        availableGames: [
          { id: 1, name: "Game 1 - Soccer", isAvailable: null },
          { id: 2, name: "Game 2 - Basketball", isAvailable: null },
          // Add more mock games here
        ]
      };
    },
    methods: {
      submitAvailability() {
        // Here we mock sending the availability data
        const availabilityData = this.availableGames.filter(game => game.isAvailable !== null);
        console.log('Submitted Availability:', availabilityData);
        // Ideally, send this data to your backend API here
        alert('Your availability has been submitted!');
      }
    }
  };
  </script>
  
  <style scoped>
  .submit-availability {
    max-width: 800px;
    margin: auto;
  }
  
  .game-item {
    border: 1px solid #e5e7eb;
  }

  .form-radio {
    border-color: #d1d5db;
  }

  .form-radio:checked {
    border-color: #9333ea;
    background-color: #9333ea;
  }
  </style> 