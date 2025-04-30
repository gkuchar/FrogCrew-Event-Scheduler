<template>
  <div class="min-h-screen bg-gray-100 p-8">
    <div class="max-w-7xl mx-auto">
      <h1 class="text-3xl font-bold text-purple-700 mb-6">Crew Templates</h1>

      <!-- Template List -->
      <div class="bg-white rounded-lg shadow-md overflow-hidden">
        <div class="p-6">
          <div class="flex justify-between items-center mb-6">
            <h2 class="text-xl font-semibold text-gray-800">Available Templates</h2>
            <button 
              @click="showNewTemplateModal = true"
              class="bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700"
            >
              Create New Template
            </button>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div 
              v-for="template in templates" 
              :key="template.id"
              class="border rounded-lg p-4 hover:shadow-lg transition-shadow"
            >
              <h3 class="text-lg font-semibold text-gray-800 mb-2">{{ template.name }}</h3>
              <p class="text-gray-600 mb-4">{{ template.description }}</p>
              <div class="flex justify-between items-center">
                <span class="text-sm text-gray-500">{{ template.positions.length }} positions</span>
                <div class="space-x-2">
                  <button 
                    @click="editTemplate(template)"
                    class="text-purple-600 hover:text-purple-800"
                  >
                    Edit
                  </button>
                  <button 
                    @click="deleteTemplate(template.id)"
                    class="text-red-600 hover:text-red-800"
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- New Template Modal -->
      <div v-if="showNewTemplateModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-lg shadow-xl max-w-2xl w-full p-6">
          <div class="flex justify-between items-start mb-4">
            <h2 class="text-2xl font-bold text-gray-900">Create New Template</h2>
            <button 
              @click="showNewTemplateModal = false"
              class="text-gray-400 hover:text-gray-500"
            >
              <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          <form @submit.prevent="saveTemplate" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700">Template Name</label>
              <input 
                v-model="newTemplate.name"
                type="text"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
                required
              >
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700">Description</label>
              <textarea 
                v-model="newTemplate.description"
                rows="3"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-purple-500 focus:ring-purple-500"
              ></textarea>
            </div>

            <div class="flex justify-end space-x-3">
              <button 
                type="button"
                @click="showNewTemplateModal = false"
                class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
              >
                Cancel
              </button>
              <button 
                type="submit"
                class="px-4 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700"
              >
                Save Template
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ManageCrew",
  data() {
    return {
      templates: [
        {
          id: 1,
          name: 'Basketball Game Template',
          description: 'Standard crew setup for basketball games',
          positions: [
            { position: 'Producer', count: 1 },
            { position: 'Director', count: 1 },
            { position: 'Technical Director', count: 1 },
            { position: 'Camera Operator', count: 4 }
          ]
        },
        {
          id: 2,
          name: 'Football Game Template',
          description: 'Standard crew setup for football games',
          positions: [
            { position: 'Producer', count: 1 },
            { position: 'Director', count: 1 },
            { position: 'Technical Director', count: 1 },
            { position: 'Camera Operator', count: 6 },
            { position: 'Replay Operator', count: 2 }
          ]
        }
      ],
      showNewTemplateModal: false,
      newTemplate: {
        name: '',
        description: '',
        positions: []
      }
    }
  },
  methods: {
    editTemplate(template) {
      // Implementation for editing a template
      console.log('Edit template:', template);
    },
    deleteTemplate(id) {
      // Implementation for deleting a template
      console.log('Delete template:', id);
    },
    saveTemplate() {
      // Implementation for saving a new template
      console.log('Save template:', this.newTemplate);
      this.showNewTemplateModal = false;
    }
  }
}
</script>
  
<style scoped>
.manage-crew {
  padding: 20px;
}
</style>
  