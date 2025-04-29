<template>
  <div class="max-w-2xl mx-auto p-6">
    <h1 class="text-2xl font-bold text-purple-700 mb-6">Invite Crew Member</h1>
    
    <form @submit.prevent="sendInvitation" class="space-y-6">
      <div>
        <label class="block text-sm font-medium text-gray-700">Email Address</label>
        <input
          v-model="email"
          type="email"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
          placeholder="Enter crew member's email"
        />
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Role</label>
        <select
          v-model="role"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
        >
          <option value="">Select a role</option>
          <option value="Producer">Producer</option>
          <option value="Technical Director">Technical Director</option>
          <option value="Camera Operator">Camera Operator</option>
          <option value="Audio Engineer">Audio Engineer</option>
          <option value="Graphics Operator">Graphics Operator</option>
        </select>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Qualified Position</label>
        <input
          v-model="qualifiedPosition"
          type="text"
          required
          class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
          placeholder="Enter qualified position"
        />
      </div>

      <div class="flex justify-end space-x-3">
        <button
          type="submit"
          class="bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700 transition-colors"
        >
          Send Invitation
        </button>
      </div>
    </form>

    <!-- Success Message -->
    <div v-if="showSuccess" class="mt-4 p-4 bg-green-100 text-green-700 rounded-md">
      Invitation sent successfully! The crew member will receive an email with registration instructions.
    </div>

    <!-- Error Message -->
    <div v-if="error" class="mt-4 p-4 bg-red-100 text-red-700 rounded-md">
      {{ error }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'InviteCrewMember',
  data() {
    return {
      email: '',
      role: '',
      qualifiedPosition: '',
      showSuccess: false,
      error: ''
    }
  },
  methods: {
    async sendInvitation() {
      try {
        // Generate a unique invitation token
        const token = this.generateToken();
        
        // Create invitation data
        const invitationData = {
          email: this.email,
          role: this.role,
          qualifiedPosition: this.qualifiedPosition,
          token: token,
          createdAt: new Date().toISOString()
        };

        // Store invitation in localStorage
        const invitations = JSON.parse(localStorage.getItem('invitations') || '[]');
        invitations.push(invitationData);
        localStorage.setItem('invitations', JSON.stringify(invitations));

        // In a real application, you would send an email here
        // For now, we'll just show a success message
        this.showSuccess = true;
        this.error = '';
        
        // Reset form
        this.email = '';
        this.role = '';
        this.qualifiedPosition = '';

        // Hide success message after 5 seconds
        setTimeout(() => {
          this.showSuccess = false;
        }, 5000);

      } catch (err) {
        this.error = 'Failed to send invitation. Please try again.';
        this.showSuccess = false;
      }
    },
    generateToken() {
      // Generate a random token
      return Math.random().toString(36).substring(2, 15) + 
             Math.random().toString(36).substring(2, 15);
    }
  }
}
</script> 