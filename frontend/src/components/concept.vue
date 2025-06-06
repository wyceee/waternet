<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Navigation -->
    <nav class="bg-white shadow-sm border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex items-center">
            <div class="flex-shrink-0 flex items-center">
              <Droplets class="h-8 w-8 text-blue-600" />
              <span class="ml-2 text-xl font-bold text-gray-900">AquaRewards</span>
            </div>
            <div class="hidden sm:ml-6 sm:flex sm:space-x-8">
              <button
                  @click="currentPage = 'dashboard'"
                  :class="currentPage === 'dashboard' ? 'border-blue-500 text-gray-900' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                  class="inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
              >
                Dashboard
              </button>
              <button
                  @click="currentPage = 'register'"
                  :class="currentPage === 'register' ? 'border-blue-500 text-gray-900' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                  class="inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
              >
                Register Measure
              </button>
              <button
                  @click="currentPage = 'supervisor'"
                  :class="currentPage === 'supervisor' ? 'border-blue-500 text-gray-900' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                  class="inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
              >
                Supervisor Panel
              </button>
              <button
                  @click="currentPage = 'wallet'"
                  :class="currentPage === 'wallet' ? 'border-blue-500 text-gray-900' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                  class="inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium"
              >
                Wallet
              </button>
            </div>
          </div>
          <div class="flex items-center">
            <button
                @click="toggleWallet"
                :class="isWalletConnected ? 'bg-green-600 hover:bg-green-700' : 'bg-blue-600 hover:bg-blue-700'"
                class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              <Wallet class="h-4 w-4 mr-2" />
              {{ isWalletConnected ? 'Connected' : 'Connect Wallet' }}
            </button>
          </div>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <!-- Dashboard -->
      <div v-if="currentPage === 'dashboard'" class="px-4 py-6 sm:px-0">
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Dashboard</h1>
          <p class="mt-2 text-gray-600">Track your water retention measures and rewards</p>
        </div>

        <!-- Stats Cards -->
        <div class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8">
          <div class="bg-white overflow-hidden shadow rounded-lg">
            <div class="p-5">
              <div class="flex items-center">
                <div class="flex-shrink-0">
                  <BarChart3 class="h-6 w-6 text-gray-400" />
                </div>
                <div class="ml-5 w-0 flex-1">
                  <dl>
                    <dt class="text-sm font-medium text-gray-500 truncate">Total Measures</dt>
                    <dd class="text-lg font-medium text-gray-900">24</dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white overflow-hidden shadow rounded-lg">
            <div class="p-5">
              <div class="flex items-center">
                <div class="flex-shrink-0">
                  <CheckCircle class="h-6 w-6 text-green-400" />
                </div>
                <div class="ml-5 w-0 flex-1">
                  <dl>
                    <dt class="text-sm font-medium text-gray-500 truncate">Approved</dt>
                    <dd class="text-lg font-medium text-gray-900">18</dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white overflow-hidden shadow rounded-lg">
            <div class="p-5">
              <div class="flex items-center">
                <div class="flex-shrink-0">
                  <Clock class="h-6 w-6 text-yellow-400" />
                </div>
                <div class="ml-5 w-0 flex-1">
                  <dl>
                    <dt class="text-sm font-medium text-gray-500 truncate">Pending</dt>
                    <dd class="text-lg font-medium text-gray-900">4</dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white overflow-hidden shadow rounded-lg">
            <div class="p-5">
              <div class="flex items-center">
                <div class="flex-shrink-0">
                  <Coins class="h-6 w-6 text-blue-400" />
                </div>
                <div class="ml-5 w-0 flex-1">
                  <dl>
                    <dt class="text-sm font-medium text-gray-500 truncate">Tokens Earned</dt>
                    <dd class="text-lg font-medium text-gray-900">1,250 AQR</dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Recent Measures -->
        <div class="bg-white shadow overflow-hidden sm:rounded-md">
          <div class="px-4 py-5 sm:px-6">
            <h3 class="text-lg leading-6 font-medium text-gray-900">Your Recent Measures</h3>
            <p class="mt-1 max-w-2xl text-sm text-gray-500">Latest water retention measures submitted</p>
          </div>
          <ul class="divide-y divide-gray-200">
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center">
                      <Leaf class="h-5 w-5 text-green-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Green Roof Installation</div>
                    <div class="text-sm text-gray-500">Submitted 2 days ago</div>
                  </div>
                </div>
                <div class="flex items-center">
                  <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                    Approved
                  </span>
                </div>
              </div>
            </li>
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-blue-100 flex items-center justify-center">
                      <Droplets class="h-5 w-5 text-blue-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Rain Barrel System</div>
                    <div class="text-sm text-gray-500">Submitted 5 days ago</div>
                  </div>
                </div>
                <div class="flex items-center">
                  <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">
                    Pending
                  </span>
                </div>
              </div>
            </li>
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-purple-100 flex items-center justify-center">
                      <TreePine class="h-5 w-5 text-purple-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Permeable Pavement</div>
                    <div class="text-sm text-gray-500">Submitted 1 week ago</div>
                  </div>
                </div>
                <div class="flex items-center">
                  <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                    Approved
                  </span>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Register Measure -->
      <div v-if="currentPage === 'register'" class="px-4 py-6 sm:px-0">
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Register Water Retention Measure</h1>
          <p class="mt-2 text-gray-600">Submit a new water retention measure for approval</p>
        </div>

        <div class="bg-white shadow sm:rounded-lg">
          <div class="px-4 py-5 sm:p-6">
            <form class="space-y-6">
              <div>
                <label for="measure-type" class="block text-sm font-medium text-gray-700">Measure Type</label>
                <select id="measure-type" class="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm rounded-md">
                  <option>Green Roof</option>
                  <option>Rain Barrel</option>
                  <option>Permeable Pavement</option>
                  <option>Rain Garden</option>
                  <option>Bioswale</option>
                </select>
              </div>

              <div>
                <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
                <textarea id="description" rows="4" class="mt-1 shadow-sm focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md" placeholder="Describe your water retention measure..."></textarea>
              </div>

              <div class="grid grid-cols-1 gap-6 sm:grid-cols-2">
                <div>
                  <label for="area" class="block text-sm font-medium text-gray-700">Area (sq ft)</label>
                  <input type="number" id="area" class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" placeholder="100">
                </div>
                <div>
                  <label for="capacity" class="block text-sm font-medium text-gray-700">Water Capacity (gallons)</label>
                  <input type="number" id="capacity" class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" placeholder="500">
                </div>
              </div>

              <div>
                <label for="location" class="block text-sm font-medium text-gray-700">Installation Location</label>
                <input type="text" id="location" class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" placeholder="123 Main St, City, State">
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700">Upload Photos</label>
                <div class="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                  <div class="space-y-1 text-center">
                    <Upload class="mx-auto h-12 w-12 text-gray-400" />
                    <div class="flex text-sm text-gray-600">
                      <label for="file-upload" class="relative cursor-pointer bg-white rounded-md font-medium text-blue-600 hover:text-blue-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-blue-500">
                        <span>Upload files</span>
                        <input id="file-upload" name="file-upload" type="file" class="sr-only" multiple>
                      </label>
                      <p class="pl-1">or drag and drop</p>
                    </div>
                    <p class="text-xs text-gray-500">PNG, JPG, GIF up to 10MB</p>
                  </div>
                </div>
              </div>

              <div class="flex justify-end">
                <button type="submit" class="ml-3 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                  Submit for Approval
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Supervisor Panel -->
      <div v-if="currentPage === 'supervisor'" class="px-4 py-6 sm:px-0">
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Supervisor Approval Panel</h1>
          <p class="mt-2 text-gray-600">Review and approve water retention measures</p>
        </div>

        <div class="bg-white shadow overflow-hidden sm:rounded-md">
          <ul class="divide-y divide-gray-200">
            <li class="px-4 py-6 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-12 w-12">
                    <div class="h-12 w-12 rounded-full bg-blue-100 flex items-center justify-center">
                      <Droplets class="h-6 w-6 text-blue-600" />
                    </div>
                  </div>
                  <div class="ml-4 flex-1">
                    <div class="text-lg font-medium text-gray-900">Rain Barrel System</div>
                    <div class="text-sm text-gray-500">Submitted by John Doe • 2 days ago</div>
                    <div class="text-sm text-gray-600 mt-1">500 gallon capacity • 123 Oak Street</div>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                    <CheckCircle class="h-4 w-4 mr-1" />
                    Approve
                  </button>
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                    <XCircle class="h-4 w-4 mr-1" />
                    Reject
                  </button>
                </div>
              </div>
            </li>
            <li class="px-4 py-6 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-12 w-12">
                    <div class="h-12 w-12 rounded-full bg-green-100 flex items-center justify-center">
                      <Leaf class="h-6 w-6 text-green-600" />
                    </div>
                  </div>
                  <div class="ml-4 flex-1">
                    <div class="text-lg font-medium text-gray-900">Green Roof Installation</div>
                    <div class="text-sm text-gray-500">Submitted by Jane Smith • 3 days ago</div>
                    <div class="text-sm text-gray-600 mt-1">200 sq ft • 456 Pine Avenue</div>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                    <CheckCircle class="h-4 w-4 mr-1" />
                    Approve
                  </button>
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                    <XCircle class="h-4 w-4 mr-1" />
                    Reject
                  </button>
                </div>
              </div>
            </li>
            <li class="px-4 py-6 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-12 w-12">
                    <div class="h-12 w-12 rounded-full bg-purple-100 flex items-center justify-center">
                      <TreePine class="h-6 w-6 text-purple-600" />
                    </div>
                  </div>
                  <div class="ml-4 flex-1">
                    <div class="text-lg font-medium text-gray-900">Permeable Pavement</div>
                    <div class="text-sm text-gray-500">Submitted by Mike Johnson • 5 days ago</div>
                    <div class="text-sm text-gray-600 mt-1">150 sq ft driveway • 789 Elm Street</div>
                  </div>
                </div>
                <div class="flex space-x-2">
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                    <CheckCircle class="h-4 w-4 mr-1" />
                    Approve
                  </button>
                  <button class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                    <XCircle class="h-4 w-4 mr-1" />
                    Reject
                  </button>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Wallet -->
      <div v-if="currentPage === 'wallet'" class="px-4 py-6 sm:px-0">
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Wallet</h1>
          <p class="mt-2 text-gray-600">Manage your AquaRewards tokens</p>
        </div>

        <!-- Balance Card -->
        <div class="bg-gradient-to-r from-blue-500 to-blue-600 rounded-lg shadow-lg p-6 text-white mb-8">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-blue-100 text-sm">Total Balance</p>
              <p class="text-3xl font-bold">1,250 AQR</p>
              <p class="text-blue-100 text-sm mt-1">≈ $125.00 USD</p>
            </div>
            <div class="text-right">
              <Coins class="h-12 w-12 text-blue-200" />
            </div>
          </div>
        </div>

        <!-- Recent Transactions -->
        <div class="bg-white shadow overflow-hidden sm:rounded-md">
          <div class="px-4 py-5 sm:px-6">
            <h3 class="text-lg leading-6 font-medium text-gray-900">Recent Transactions</h3>
            <p class="mt-1 max-w-2xl text-sm text-gray-500">Your latest token transactions</p>
          </div>
          <ul class="divide-y divide-gray-200">
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center">
                      <Plus class="h-5 w-5 text-green-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Reward for Green Roof</div>
                    <div class="text-sm text-gray-500">2 days ago • Tx: 0x1a2b3c...</div>
                  </div>
                </div>
                <div class="text-right">
                  <div class="text-sm font-medium text-green-600">+75 AQR</div>
                  <div class="text-sm text-gray-500">$7.50</div>
                </div>
              </div>
            </li>
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center">
                      <Plus class="h-5 w-5 text-green-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Reward for Rain Garden</div>
                    <div class="text-sm text-gray-500">1 week ago • Tx: 0x4d5e6f...</div>
                  </div>
                </div>
                <div class="text-right">
                  <div class="text-sm font-medium text-green-600">+50 AQR</div>
                  <div class="text-sm text-gray-500">$5.00</div>
                </div>
              </div>
            </li>
            <li class="px-4 py-4 sm:px-6">
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <div class="flex-shrink-0 h-10 w-10">
                    <div class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center">
                      <Plus class="h-5 w-5 text-green-600" />
                    </div>
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">Reward for Permeable Pavement</div>
                    <div class="text-sm text-gray-500">2 weeks ago • Tx: 0x7g8h9i...</div>
                  </div>
                </div>
                <div class="text-right">
                  <div class="text-sm font-medium text-green-600">+100 AQR</div>
                  <div class="text-sm text-gray-500">$10.00</div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import {
  Droplets,
  Wallet,
  BarChart3,
  CheckCircle,
  Clock,
  Coins,
  Leaf,
  TreePine,
  Upload,
  XCircle,
  Plus
} from 'lucide-vue-next'

const currentPage = ref('dashboard')
const isWalletConnected = ref(false)

const toggleWallet = () => {
  isWalletConnected.value = !isWalletConnected.value
}
</script>

<style scoped>
/* Example of custom styles */
.bg-gradient-to-r {
  background: linear-gradient(to right, #3b82f6, #2563eb);
}

.text-blue-100 {
  color: #dbeafe;
}

.text-blue-200 {
  color: #bfdbfe;
}

/* General Layout */
.min-h-screen {
  min-height: 100vh;
}

.bg-gray-50 {
  background-color: #f9fafb;
}

.bg-white {
  background-color: #ffffff;
}

.shadow-sm {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

.border-b {
  border-bottom: 1px solid #e5e7eb;
}

.max-w-7xl {
  max-width: 80rem;
}

.mx-auto {
  margin-left: auto;
  margin-right: auto;
}

.px-4 {
  padding-left: 1rem;
  padding-right: 1rem;
}

.sm\:px-6 {
  padding-left: 1.5rem;
  padding-right: 1.5rem;
}

.lg\:px-8 {
  padding-left: 2rem;
  padding-right: 2rem;
}

.h-16 {
  height: 4rem;
}

.flex {
  display: flex;
}

.justify-between {
  justify-content: space-between;
}

.items-center {
  align-items: center;
}

.flex-shrink-0 {
  flex-shrink: 0;
}

.hidden {
  display: none;
}

.sm\:ml-6 {
  margin-left: 1.5rem;
}

.sm\:flex {
  display: flex;
}

.sm\:space-x-8 > :not([hidden]) ~ :not([hidden]) {
  margin-left: 2rem;
}

/* Buttons */
.inline-flex {
  display: inline-flex;
}

.items-center {
  align-items: center;
}

.px-1 {
  padding-left: 0.25rem;
  padding-right: 0.25rem;
}

.pt-1 {
  padding-top: 0.25rem;
}

.border-b-2 {
  border-bottom-width: 2px;
}

.text-sm {
  font-size: 0.875rem;
  line-height: 1.25rem;
}

.font-medium {
  font-weight: 500;
}

.text-gray-900 {
  color: #111827;
}

.text-gray-500 {
  color: #6b7280;
}

.hover\:text-gray-700:hover {
  color: #374151;
}

.hover\:border-gray-300:hover {
  border-color: #d1d5db;
}

.border-transparent {
  border-color: transparent;
}

.border-blue-500 {
  border-color: #3b82f6;
}

.bg-blue-600 {
  background-color: #2563eb;
}

.hover\:bg-blue-700:hover {
  background-color: #1d4ed8;
}

.bg-green-600 {
  background-color: #16a34a;
}

.hover\:bg-green-700:hover {
  background-color: #15803d;
}

.text-white {
  color: #ffffff;
}

.rounded-md {
  border-radius: 0.375rem;
}

.focus\:outline-none:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
}

.focus\:ring-2:focus {
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.5);
}

.focus\:ring-offset-2:focus {
  box-shadow: 0 0 0 2px rgba(243, 244, 246, 1), 0 0 0 4px rgba(59, 130, 246, 0.5);
}

.focus\:ring-blue-500:focus {
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.5);
}

/* Typography */
.text-xl {
  font-size: 1.25rem;
  line-height: 1.75rem;
}

.font-bold {
  font-weight: 700;
}

.text-gray-600 {
  color: #4b5563;
}

.text-3xl {
  font-size: 1.875rem;
  line-height: 2.25rem;
}

/* Grid */
.grid {
  display: grid;
}

.grid-cols-1 {
  grid-template-columns: repeat(1, minmax(0, 1fr));
}

.sm\:grid-cols-2 {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.lg\:grid-cols-4 {
  grid-template-columns: repeat(4, minmax(0, 1fr));
}

.gap-5 {
  gap: 1.25rem;
}

.mb-8 {
  margin-bottom: 2rem;
}

/* Cards */
.bg-white {
  background-color: #ffffff;
}

.overflow-hidden {
  overflow: hidden;
}

.shadow {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.1);
}

.rounded-lg {
  border-radius: 0.5rem;
}

.p-5 {
  padding: 1.25rem;
}

.ml-5 {
  margin-left: 1.25rem;
}

.w-0 {
  width: 0;
}

.flex-1 {
  flex: 1 1 0%;
}

/* Additional Styles */
.bg-gradient-to-r {
  background: linear-gradient(to right, #3b82f6, #2563eb);
}

.text-blue-100 {
  color: #dbeafe;
}

.text-blue-200 {
  color: #bfdbfe;
}
</style>