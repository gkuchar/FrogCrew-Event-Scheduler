<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8">
      <div>
        <img class="mx-auto h-24 w-auto" src="../assets/logo.png" alt="FrogCrew Logo" />
        <h2 class="mt-6 text-center text-3xl font-extrabold text-purple-700">
          Welcome to FrogCrew
        </h2>
      </div>

      <!-- Tabs -->
      <div class="flex border-b border-gray-200">
        <button
          @click="activeTab = 'login'"
          class="flex-1 py-4 px-1 text-center border-b-2 font-medium text-sm"
          :class="activeTab === 'login' ? 'border-purple-500 text-purple-600' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
        >
          Login
        </button>
        <button
          @click="activeTab = 'register'"
          class="flex-1 py-4 px-1 text-center border-b-2 font-medium text-sm"
          :class="activeTab === 'register' ? 'border-purple-500 text-purple-600' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
        >
          Register
        </button>
      </div>

      <!-- User Type Selector -->
      <div class="mt-4">
        <label class="block text-sm font-medium text-gray-700">Log in/Register as:</label>
        <select
          v-model="userType"
          class="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-purple-500 focus:border-purple-500 sm:text-sm rounded-md"
        >
          <option value="crew">Crew Member</option>
          <option value="admin">Admin</option>
        </select>
      </div>

      <!-- Login Form -->
      <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="mt-8 space-y-6">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="email" class="sr-only">Email address</label>
            <input
              id="email"
              v-model="email"
              type="email"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Email address"
            />
          </div>
          <div>
            <label for="password" class="sr-only">Password</label>
            <input
              id="password"
              v-model="password"
              type="password"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Password"
            />
          </div>
        </div>

        <div class="flex items-center justify-between">
          <div class="text-sm">
            <a href="#" class="font-medium text-purple-600 hover:text-purple-500">
              Forgot your password?
            </a>
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
          >
            Sign in
          </button>
        </div>
      </form>

      <!-- Register Form -->
      <form v-else @submit.prevent="handleRegister" class="mt-8 space-y-6">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="firstName" class="sr-only">First Name</label>
            <input
              id="firstName"
              v-model="form.firstName"
              type="text"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="First Name"
            />
          </div>
          <div>
            <label for="lastName" class="sr-only">Last Name</label>
            <input
              id="lastName"
              v-model="form.lastName"
              type="text"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Last Name"
            />
          </div>
          <div>
            <label for="registerEmail" class="sr-only">Email address</label>
            <input
              id="registerEmail"
              v-model="form.email"
              type="email"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Email address"
            />
          </div>
          <div>
            <label for="phone" class="sr-only">Phone Number</label>
            <input
              id="phone"
              v-model="form.phone"
              type="tel"
              required
              pattern="\d{3}-\d{3}-\d{4}"
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Phone (999-999-9999)"
            />
          </div>
          <div>
            <label for="registerPassword" class="sr-only">Password</label>
            <input
              id="registerPassword"
              v-model="form.password"
              type="password"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Password"
            />
          </div>
          <div>
            <label for="role" class="sr-only">Role</label>
            <input
              id="role"
              v-model="form.role"
              type="text"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Role"
            />
          </div>
          <div>
            <label for="qualifiedPosition" class="sr-only">Qualified Position</label>
            <input
              id="qualifiedPosition"
              v-model="form.qualifiedPosition"
              type="text"
              required
              class="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-purple-500 focus:border-purple-500 focus:z-10 sm:text-sm"
              placeholder="Qualified Position"
            />
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
          >
            Register
          </button>
        </div>
      </form>

      <!-- Error Message -->
      <div v-if="error" class="mt-4 text-center text-red-600">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      activeTab: 'login',
      userType: 'crew',
      email: '',
      password: '',
      form: {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        password: '',
        role: '',
        qualifiedPosition: ''
      },
      error: '',
      // Sample users for testing
      sampleUsers: [
        {
          id: 1,
          email: 'admin@tcu.edu',
          password: 'admin123',
          role: 'Admin',
          firstName: 'Admin',
          lastName: 'User'
        },
        {
          id: 2,
          email: 'crew@tcu.edu',
          password: 'crew123',
          role: 'Crew',
          firstName: 'Crew',
          lastName: 'Member'
        }
      ]
    }
  },
  created() {
    // Check for invitation token in URL
    const token = this.$route.query.token
    if (token) {
      this.activeTab = 'register'
      // Validate token and pre-fill form
      this.validateInvitationToken(token)
    }
  },
  methods: {
    handleLogin() {
      // Find user with matching credentials
      const user = this.sampleUsers.find(
        u => u.email === this.email && u.password === this.password
      )

      if (user) {
        // Store user in localStorage
        localStorage.setItem('currentUser', JSON.stringify(user))
        // Redirect to home page
        this.$router.push('/home')
      } else {
        this.error = 'Invalid email or password'
      }
    },
    handleRegister() {
      // For demo purposes, just show success message
      alert('Registration successful! Please log in.')
      this.activeTab = 'login'
      this.form = {
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        password: '',
        role: '',
        qualifiedPosition: ''
      }
    },
    validateInvitationToken(token) {
      // Get invitations from localStorage
      const invitations = JSON.parse(localStorage.getItem('invitations') || '[]')
      const invitation = invitations.find(inv => inv.token === token)

      if (invitation) {
        // Pre-fill form with invitation data
        this.form.email = invitation.email
        this.form.role = invitation.role
        this.form.qualifiedPosition = invitation.qualifiedPosition
      } else {
        this.error = 'Invalid invitation token'
      }
    }
  }
}
</script>

<style scoped>
/* You can add additional styles if needed */
</style>
