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
</style>