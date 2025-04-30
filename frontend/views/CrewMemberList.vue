<template>
  <div class="min-h-screen bg-gray-100 p-8">
    <div class="max-w-6xl mx-auto">
      <h1 class="text-3xl font-bold text-center mb-8">List of Crew Members</h1>
      <div v-if="isAdmin" class="mb-4 flex justify-center">
        <span class="inline-block bg-green-100 text-green-800 text-sm font-semibold px-4 py-2 rounded-full">Admin Mode: You have permission to delete, sort, and search crew members.</span>
      </div>

      <!-- Admin Controls -->
      <div v-if="isAdmin" class="flex flex-col md:flex-row md:items-center md:space-x-4 mb-4 space-y-2 md:space-y-0">
        <input v-model="searchQuery" type="text" placeholder="Search by name or email..." class="rounded border-gray-300 px-3 py-2 focus:ring-purple-500 focus:border-purple-500" />
        <select v-model="sortBy" class="rounded border-gray-300 px-3 py-2 focus:ring-purple-500 focus:border-purple-500">
          <option value="name">Sort by Name</option>
          <option value="role">Sort by Role</option>
        </select>
      </div>

      <!-- Crew Members Table -->
      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full">
          <thead>
            <tr class="bg-purple-600 text-white">
              <th class="px-6 py-3 text-left text-sm font-semibold">Name</th>
              <th class="px-6 py-3 text-left text-sm font-semibold">Email</th>
              <th class="px-6 py-3 text-left text-sm font-semibold">Phone number</th>
              <th class="px-6 py-3 text-left text-sm font-semibold bg-purple-600 text-white">Actions</th>
              <th v-if="isAdmin" class="px-6 py-3 text-left text-sm font-semibold">Actions</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200">
            <tr v-for="member in filteredAndSortedMembers" :key="member.id" class="hover:bg-gray-50">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ member.firstName }} {{ member.lastName }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                {{ member.email }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                {{ member.phone }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-right">
                <button
                  @click="showDetails(member)"
                  style="background: #7c3aed; color: #fff; font-weight: bold; padding: 8px 16px; border-radius: 6px; border: none; cursor: pointer; font-size: 1rem; margin-right: 8px;"
                  class="shadow-lg"
                >
                  MORE
                </button>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-right">
                <button v-if="isAdmin" @click="deleteMember(member.id)" style="background: #7c3aed; color: #fff; font-weight: bold; padding: 8px 16px; border-radius: 6px; border: none; cursor: pointer; font-size: 1rem;" class="ml-2 shadow-lg">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Details Modal -->
      <div v-if="selectedMember" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-lg shadow-xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
          <div class="p-6">
            <div class="flex justify-between items-start mb-4">
              <h2 class="text-2xl font-bold text-gray-900">
                {{ selectedMember.firstName }} {{ selectedMember.lastName }}
              </h2>
              <button 
                @click="selectedMember = null"
                class="text-gray-400 hover:text-gray-500"
              >
                <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>

            <div class="grid grid-cols-2 gap-6">
              <!-- Personal Information -->
              <div class="space-y-4">
                <h3 class="text-lg font-semibold text-gray-900">Personal Information</h3>
                
                <div>
                  <label class="block text-sm font-medium text-gray-600">Email</label>
                  <p class="mt-1">{{ selectedMember.email }}</p>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-600">Phone</label>
                  <p class="mt-1">{{ selectedMember.phone }}</p>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-600">Address</label>
                  <p class="mt-1">{{ selectedMember.address }}</p>
                </div>
              </div>

              <!-- Professional Information -->
              <div class="space-y-4">
                <h3 class="text-lg font-semibold text-gray-900">Professional Information</h3>
                
                <div>
                  <label class="block text-sm font-medium text-gray-600">Role</label>
                  <p class="mt-1">{{ selectedMember.role }}</p>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-600">Qualified Position</label>
                  <p class="mt-1">{{ selectedMember.qualifiedPosition }}</p>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-600">Experience</label>
                  <p class="mt-1">{{ selectedMember.experience }} years</p>
                </div>
              </div>
            </div>

            <!-- Contact Actions -->
            <div class="mt-6 flex space-x-4">
              <a 
                :href="'mailto:' + selectedMember.email"
                class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
              >
                Send Email
              </a>
              <a 
                :href="'tel:' + selectedMember.phone"
                class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-purple-600 hover:bg-purple-700"
              >
                Call
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CrewMemberList',
  data() {
    return {
      crewMembers: [
        {
          id: 1,
          firstName: 'John',
          lastName: 'Doe',
          email: 'john.doe@example.com',
          phone: '123-456-7890',
          address: '123 Main St, City, State 12345',
          role: 'Producer',
          qualifiedPosition: 'Senior Producer',
          experience: 8
        },
        {
          id: 2,
          firstName: 'Jane',
          lastName: 'Smith',
          email: 'jane.smith@example.com',
          phone: '111-222-3333',
          address: '456 Oak Ave, City, State 12345',
          role: 'Director',
          qualifiedPosition: 'Technical Director',
          experience: 6
        }
      ],
      selectedMember: null,
      isAdmin: false,
      searchQuery: '',
      sortBy: 'name'
    };
  },
  created() {
    console.log('CrewMemberList component created');
    // Try to get crew members from localStorage, if none exist, use the default data
    const stored = localStorage.getItem('crewMembers');
    console.log('Stored crew members:', stored);
    if (stored) {
      this.crewMembers = JSON.parse(stored);
    } else {
      // Store the default data in localStorage
      localStorage.setItem('crewMembers', JSON.stringify(this.crewMembers));
    }
    console.log('Current crew members:', this.crewMembers);

    // Add admin user
    const users = JSON.parse(localStorage.getItem('crewMembers') || '[]');
    users.push({
      id: 999,
      email: 'admin@tcu.edu',
      password: 'admin123',
      role: 'Admin',
      firstName: 'Admin',
      lastName: 'User',
      phone: '999-999-9999',
      qualifiedPosition: 'Manager'
    });
    localStorage.setItem('crewMembers', JSON.stringify(users));

    // Set isAdmin from localStorage
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    this.isAdmin = currentUser?.role === 'Admin';
  },
  computed: {
    filteredAndSortedMembers() {
      let members = [...this.crewMembers];
      // Search
      if (this.isAdmin && this.searchQuery) {
        const q = this.searchQuery.toLowerCase();
        members = members.filter(m =>
          m.firstName.toLowerCase().includes(q) ||
          m.lastName.toLowerCase().includes(q) ||
          m.email.toLowerCase().includes(q)
        );
      }
      // Sort
      if (this.isAdmin && this.sortBy === 'name') {
        members.sort((a, b) => (a.firstName + a.lastName).localeCompare(b.firstName + b.lastName));
      } else if (this.isAdmin && this.sortBy === 'role') {
        members.sort((a, b) => (a.role || '').localeCompare(b.role || ''));
      }
      return members;
    }
  },
  methods: {
    showDetails(member) {
      this.selectedMember = member;
    },
    deleteMember(id) {
      this.crewMembers = this.crewMembers.filter(m => m.id !== id);
      localStorage.setItem('crewMembers', JSON.stringify(this.crewMembers));
    }
  }
}
</script>
