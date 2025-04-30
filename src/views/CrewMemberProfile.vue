<template>
  <div class="max-w-4xl mx-auto p-6">
    <div v-if="!isAuthenticated" class="text-center py-12">
      <p class="text-gray-600">Please log in to view crew member profiles.</p>
      <router-link to="/login" class="text-purple-600 hover:text-purple-800 mt-4 inline-block">
        Go to Login
      </router-link>
    </div>

    <div v-else-if="crewMember" class="bg-white rounded-lg shadow-lg overflow-hidden">
      <!-- Profile Header -->
      <div class="bg-purple-700 text-white p-6">
        <div class="flex justify-between items-start">
          <div>
            <h1 class="text-3xl font-bold">{{ crewMember.firstName }} {{ crewMember.lastName }}</h1>
            <p class="mt-2 text-purple-200">{{ crewMember.qualifiedPosition }}</p>
          </div>
          <button 
            @click="$router.push('/crewlist')" 
            class="text-white hover:text-purple-200 flex items-center"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Back to Crew List
          </button>
        </div>
      </div>

      <!-- Profile Content -->
      <div class="p-6">
        <!-- Contact Information -->
        <div class="mb-8">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Contact Information</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="bg-gray-50 p-4 rounded-lg">
              <div class="flex items-center">
                <svg class="w-5 h-5 text-purple-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
                <div>
                  <label class="block text-sm font-medium text-gray-600">Email</label>
                  <p class="mt-1 text-lg text-gray-900">{{ crewMember.email }}</p>
                </div>
              </div>
            </div>
            
            <div class="bg-gray-50 p-4 rounded-lg">
              <div class="flex items-center">
                <svg class="w-5 h-5 text-purple-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
                <div>
                  <label class="block text-sm font-medium text-gray-600">Phone Number</label>
                  <p class="mt-1 text-lg text-gray-900">{{ formatPhoneNumber(crewMember.phone) }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Professional Information -->
        <div class="mb-8">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Professional Information</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="bg-gray-50 p-4 rounded-lg">
              <label class="block text-sm font-medium text-gray-600">Primary Position</label>
              <p class="mt-1 text-lg text-gray-900">{{ crewMember.qualifiedPosition }}</p>
            </div>
            
            <div class="bg-gray-50 p-4 rounded-lg">
              <label class="block text-sm font-medium text-gray-600">Role</label>
              <p class="mt-1 text-lg text-gray-900">{{ crewMember.role }}</p>
            </div>

            <div class="bg-gray-50 p-4 rounded-lg">
              <label class="block text-sm font-medium text-gray-600">Experience Level</label>
              <p class="mt-1 text-lg text-gray-900">{{ crewMember.experienceLevel || 'Not specified' }}</p>
            </div>

            <div class="bg-gray-50 p-4 rounded-lg">
              <label class="block text-sm font-medium text-gray-600">Availability Status</label>
              <p class="mt-1 text-lg text-gray-900">{{ crewMember.status || 'Active' }}</p>
            </div>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="flex space-x-4 mt-6">
          <a 
            :href="'mailto:' + crewMember.email"
            class="flex-1 inline-flex justify-center items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
            Send Email
          </a>
          <a 
            :href="'tel:' + crewMember.phone"
            class="flex-1 inline-flex justify-center items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
            </svg>
            Call
          </a>
        </div>
      </div>
    </div>

    <!-- Loading or Error State -->
    <div v-else class="text-center py-12">
      <p class="text-gray-600">Crew member not found.</p>
      <router-link to="/crewlist" class="text-purple-600 hover:text-purple-800 mt-4 inline-block">
        Back to Crew List
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CrewMemberProfile',
  data() {
    return {
      crewMember: null,
      isAuthenticated: false,
      isAdmin: false,
      currentUser: null
    }
  },
  created() {
    // Check if user is authenticated
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.isAuthenticated = !!currentUser;
    this.currentUser = currentUser;

    if (!this.isAuthenticated) {
      return;
    }

    // Check if user is admin
    this.isAdmin = currentUser?.role === 'Admin';

    // Get the crew member ID from the route
    const crewMemberId = parseInt(this.$route.params.id);
    
    // Get crew members from localStorage
    const crewMembers = JSON.parse(localStorage.getItem('crewMembers') || '[]');
    
    // Find the specific crew member
    this.crewMember = crewMembers.find(member => member.id === crewMemberId);

    // If crew member not found, redirect to crew list
    if (!this.crewMember) {
      this.$router.push('/crewlist');
    }
  },
  methods: {
    formatPhoneNumber(phone) {
      if (!phone) return 'Not provided';
      // Format: (XXX) XXX-XXXX
      const cleaned = phone.toString().replace(/\D/g, '');
      const match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
      if (match) {
        return '(' + match[1] + ') ' + match[2] + '-' + match[3];
      }
      return phone;
    }
  }
}
</script>

<style scoped>
.bg-gradient-to-b {
  background-image: linear-gradient(to bottom, var(--tw-gradient-stops));
}
</style> 