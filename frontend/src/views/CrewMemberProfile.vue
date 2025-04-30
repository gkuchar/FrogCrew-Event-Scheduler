<template>
  <div class="max-w-4xl mx-auto p-6">
    <div v-if="!isAuthenticated" class="text-center py-12">
      <p class="text-gray-600">Please log in to view crew member profiles.</p>
      <router-link to="/login" class="text-purple-600 hover:text-purple-800 mt-4 inline-block">
        Go to Login
      </router-link>
    </div>

    <div v-else-if="crewMember" class="bg-white rounded-lg shadow-lg p-6">
      <!-- Header with back button -->
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold text-purple-700">Crew Member Profile</h1>
        <button 
          @click="$router.push('/crewlist')" 
          class="text-purple-600 hover:text-purple-800 flex items-center"
        >
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
          </svg>
          Back to Crew List
        </button>
      </div>

      <!-- Profile Information -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Basic Information -->
        <div class="space-y-4">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Basic Information</h2>
          
          <div>
            <label class="block text-sm font-medium text-gray-600">Full Name</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.firstName }} {{ crewMember.lastName }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-600">Email</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.email }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-600">Phone</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.phone }}</p>
          </div>
        </div>

        <!-- Professional Information -->
        <div class="space-y-4">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Professional Information</h2>
          
          <div>
            <label class="block text-sm font-medium text-gray-600">Role</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.role }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-600">Qualified Position</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.qualifiedPosition }}</p>
          </div>
        </div>
      </div>

      <!-- Contact Actions -->
      <div class="mt-8 pt-6 border-t border-gray-200">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">Contact Actions</h2>
        <div class="flex space-x-4">
          <a 
            :href="'mailto:' + crewMember.email"
            class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
            Send Email
          </a>
          <a 
            :href="'tel:' + crewMember.phone"
            class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
          >
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
            </svg>
            Call
          </a>
        </div>
      </div>

      <!-- Additional Information (Admin Only) -->
      <div v-if="isAdmin" class="mt-8 pt-6 border-t border-gray-200">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">Additional Information (Admin Only)</h2>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-600">Account Status</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.status || 'Active' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-600">Last Login</label>
            <p class="mt-1 text-lg text-gray-900">{{ crewMember.lastLogin || 'Never' }}</p>
          </div>
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
    // Method to handle contact actions
    handleContact(type) {
      if (type === 'email') {
        window.location.href = `mailto:${this.crewMember.email}`;
      } else if (type === 'phone') {
        window.location.href = `tel:${this.crewMember.phone}`;
      }
    }
  }
}
</script> 