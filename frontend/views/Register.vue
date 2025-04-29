<template>
  <div class="min-h-screen bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md mx-auto bg-white rounded-lg shadow-lg p-8">
      <div class="text-center mb-8">
        <h2 class="text-3xl font-bold text-purple-700">Create Your Account</h2>
        <p class="mt-2 text-gray-600">Please fill out the form below to complete your registration</p>
      </div>

      <!-- Registration Form -->
      <form v-if="!showConfirmation" @submit.prevent="validateAndSubmit" class="space-y-6">
        <!-- First Name -->
        <div>
          <label class="block text-sm font-medium text-gray-700">First Name *</label>
          <input
            v-model="form.firstName"
            type="text"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.firstName}"
          />
          <p v-if="errors.firstName" class="mt-1 text-sm text-red-600">{{ errors.firstName }}</p>
        </div>

        <!-- Last Name -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Last Name *</label>
          <input
            v-model="form.lastName"
            type="text"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.lastName}"
          />
          <p v-if="errors.lastName" class="mt-1 text-sm text-red-600">{{ errors.lastName }}</p>
        </div>

        <!-- Email -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Email *</label>
          <input
            v-model="form.email"
            type="email"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.email}"
          />
          <p v-if="errors.email" class="mt-1 text-sm text-red-600">{{ errors.email }}</p>
        </div>

        <!-- Phone Number -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Phone Number *</label>
          <input
            v-model="form.phone"
            type="tel"
            required
            placeholder="999-999-9999"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.phone}"
          />
          <p v-if="errors.phone" class="mt-1 text-sm text-red-600">{{ errors.phone }}</p>
        </div>

        <!-- Password -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Password *</label>
          <input
            v-model="form.password"
            type="password"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.password}"
          />
          <p v-if="errors.password" class="mt-1 text-sm text-red-600">{{ errors.password }}</p>
        </div>

        <!-- Role -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Role *</label>
          <select
            v-model="form.role"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.role}"
          >
            <option value="">Select a role</option>
            <option value="Admin">Admin</option>
            <option value="Camera">Camera</option>
            <option value="Replay">Replay</option>
            <option value="Audio">Audio</option>
            <option value="Graphics">Graphics</option>
            <option value="Technical Director">Technical Director</option>
          </select>
          <p v-if="errors.role" class="mt-1 text-sm text-red-600">{{ errors.role }}</p>
        </div>

        <!-- Qualified Position -->
        <div>
          <label class="block text-sm font-medium text-gray-700">Qualified Position *</label>
          <input
            v-model="form.qualifiedPosition"
            type="text"
            required
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
            :class="{'border-red-500': errors.qualifiedPosition}"
          />
          <p v-if="errors.qualifiedPosition" class="mt-1 text-sm text-red-600">{{ errors.qualifiedPosition }}</p>
        </div>

        <div class="flex justify-end">
          <button
            type="submit"
            class="w-full bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2"
          >
            Continue
          </button>
        </div>
      </form>

      <!-- Confirmation Screen -->
      <div v-else class="space-y-6">
        <h3 class="text-xl font-semibold text-gray-900">Please Confirm Your Information</h3>
        
        <div class="bg-gray-50 p-4 rounded-md">
          <dl class="space-y-4">
            <div>
              <dt class="text-sm font-medium text-gray-500">Full Name</dt>
              <dd class="mt-1 text-sm text-gray-900">{{ form.firstName }} {{ form.lastName }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Email</dt>
              <dd class="mt-1 text-sm text-gray-900">{{ form.email }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Phone</dt>
              <dd class="mt-1 text-sm text-gray-900">{{ form.phone }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Role</dt>
              <dd class="mt-1 text-sm text-gray-900">{{ form.role }}</dd>
            </div>
            <div>
              <dt class="text-sm font-medium text-gray-500">Qualified Position</dt>
              <dd class="mt-1 text-sm text-gray-900">{{ form.qualifiedPosition }}</dd>
            </div>
          </dl>
        </div>

        <div class="flex space-x-4">
          <button
            @click="showConfirmation = false"
            class="flex-1 bg-gray-100 text-gray-700 py-2 px-4 rounded-md hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
          >
            Edit
          </button>
          <button
            @click="confirmRegistration"
            class="flex-1 bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2"
          >
            Confirm Registration
          </button>
        </div>
      </div>

      <!-- Success Message -->
      <div v-if="showSuccess" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center">
        <div class="bg-white p-8 rounded-lg shadow-xl max-w-md w-full">
          <div class="text-center">
            <svg class="mx-auto h-12 w-12 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
            <h3 class="mt-4 text-lg font-medium text-gray-900">Registration Successful!</h3>
            <p class="mt-2 text-sm text-gray-500">Your account has been created successfully.</p>
            <div class="mt-6">
              <router-link
                to="/login"
                class="inline-flex justify-center px-4 py-2 text-sm font-medium text-white bg-purple-600 border border-transparent rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
              >
                Go to Login
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      form: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        password: '',
        role: '',
        qualifiedPosition: ''
      },
      errors: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        password: '',
        role: '',
        qualifiedPosition: ''
      },
      showConfirmation: false,
      showSuccess: false
    }
  },
  methods: {
    validateAndSubmit() {
      // Reset errors
      this.errors = {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        password: '',
        role: '',
        qualifiedPosition: ''
      }

      let isValid = true

      // Validate First Name
      if (!this.form.firstName.trim()) {
        this.errors.firstName = 'First name is required'
        isValid = false
      }

      // Validate Last Name
      if (!this.form.lastName.trim()) {
        this.errors.lastName = 'Last name is required'
        isValid = false
      }

      // Validate Email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (!this.form.email.trim()) {
        this.errors.email = 'Email is required'
        isValid = false
      } else if (!emailRegex.test(this.form.email)) {
        this.errors.email = 'Please enter a valid email address'
        isValid = false
      }

      // Validate Phone
      const phoneRegex = /^\d{3}-\d{3}-\d{4}$/
      if (!this.form.phone.trim()) {
        this.errors.phone = 'Phone number is required'
        isValid = false
      } else if (!phoneRegex.test(this.form.phone)) {
        this.errors.phone = 'Phone number must be in format 999-999-9999'
        isValid = false
      }

      // Validate Password
      if (!this.form.password.trim()) {
        this.errors.password = 'Password is required'
        isValid = false
      }

      // Validate Role
      if (!this.form.role) {
        this.errors.role = 'Please select a role'
        isValid = false
      }

      // Validate Qualified Position
      if (!this.form.qualifiedPosition.trim()) {
        this.errors.qualifiedPosition = 'Qualified position is required'
        isValid = false
      }

      if (isValid) {
        this.showConfirmation = true
      }
    },

    confirmRegistration() {
      // Get existing crew members
      const crewMembers = JSON.parse(localStorage.getItem('crewMembers') || '[]')
      
      // Check if email already exists
      if (crewMembers.some(member => member.email === this.form.email)) {
        this.errors.email = 'An account with this email already exists'
        this.showConfirmation = false
        return
      }

      // Create new crew member
      const newCrewMember = {
        id: crewMembers.length + 1,
        ...this.form,
        status: 'Active',
        lastLogin: null
      }

      // Add to crew members
      crewMembers.push(newCrewMember)
      localStorage.setItem('crewMembers', JSON.stringify(crewMembers))

      // Show success message
      this.showSuccess = true

      // Redirect to login after 3 seconds
      setTimeout(() => {
        this.$router.push('/login')
      }, 3000)
    }
  }
}
</script>
  