<template>
  <div>
    <div class="header">
      <h1 class="title">Supervisor Approval Panel</h1>
      <p>Review and approve water retention measures</p>
    </div>

    <div v-if="notification.message" :class="['notification', notification.type]">
      {{ notification.message }}
    </div>

    <!-- PENDING -->
    <div class="panel">
      <h2 class="subtitle">Pending Measures</h2>
      <table class="measure-table">
        <thead>
        <tr>
          <th></th>
          <th>Submitted</th>
          <th>User</th>
          <th>Description</th>
          <th>Type</th>
          <th>Area</th>
          <th>Capacity</th>
          <th>Location</th>
          <th>Status</th>
          <th>Photo</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in pendingMeasures" :key="item.id">

          <td>
            <div :class="['icon-bg', getIconBg(item.measureType)]">
              <component :is="getIcon(item.measureType)" class="icon" :class="getIconColor(item.measureType)" />
            </div>
          </td>
          <td>{{ getRelativeTime(item.timestamp) }}</td>
          <td>{{ item.user?.name || 'Unknown' }}</td>
          <td>{{ item.description }}</td>
          <td>{{ item.measureType }}</td>
          <td>{{ item.area }} m²</td>
          <td>{{ item.capacity }} L</td>
          <td>{{ item.location }}</td>
          <td>
            <span :class="['status-badge', getStatusClass(item.status)]">
              {{ item.status }}
            </span>
          </td>
          <td>
            <button class="photo-link" @click="openPhoto(item.photoUrl)">View photo</button>
          </td>
          <td>
            <button class="btn approve" @click="approve(item.id)">
              <CheckCircle class="btn-icon" /> Approve
            </button>
            <button class="btn reject" @click="reject(item.id)">
              <XCircle class="btn-icon" /> Reject
            </button>
          </td>
        </tr>
        <tr v-if="pendingMeasures.length === 0">
          <td colspan="11" style="text-align: center; padding: 1rem; color: #6b7280;">
            No pending measures found.
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- REVIEWED -->
    <div class="panel" style="margin-top: 5rem;">
      <h2 class="subtitle">Reviewed Measures</h2>
      <table class="measure-table">
        <thead>
        <tr>
          <th></th>
          <th>Submitted</th>
          <th>User</th>
          <th>Description</th>
          <th>Type</th>
          <th>Area</th>
          <th>Capacity</th>
          <th>Location</th>
          <th>Status</th>
          <th>Transaction</th>
          <th>Photo</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in reviewedMeasures" :key="item.id">
          <td>
            <div :class="['icon-bg', getIconBg(item.measureType)]">
              <component :is="getIcon(item.measureType)" class="icon" :class="getIconColor(item.measureType)" />
            </div>
          </td>
          <td>{{ getRelativeTime(item.timestamp) }}</td>
          <td>{{ item.user?.name || 'Unknown' }}</td>
          <td>{{ item.description }}</td>
          <td>{{ item.measureType }}</td>
          <td>{{ item.area }} m²</td>
          <td>{{ item.capacity }} L</td>
          <td>{{ item.location }}</td>
          <td>
            <span :class="['status-badge', getStatusClass(item.status)]">
              {{ item.status }}
            </span>
          </td>
          <td>
            <template v-if="item.txHash">
              <a
                  :href="`https://sepolia.etherscan.io/tx/${item.txHash}`"
                  target="_blank"
                  rel="noopener"
                  style="color: #3b82f6; text-decoration: underline;"
              >
                View TX
              </a>
            </template>
            <template v-else>
              -
            </template>
          </td>
          <td>
            <button class="photo-link" @click="openPhoto(item.photoUrl)">View photo</button>
          </td>
        </tr>
        <tr v-if="reviewedMeasures.length === 0">
          <td colspan="10" style="text-align: center; padding: 1rem; color: #6b7280;">
            No reviewed measures available.
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <img :src="selectedPhotoUrl" alt="Measure Photo" />
      <button class="close-btn" @click="closeModal">Close</button>
    </div>
  </div>
  <div v-if="isLoading" class="modal-overlay">
    <div class="modal-content">
      <p>Processing transaction, please wait...</p>
      <div class="spinner"></div>
    </div>
  </div>
  <div v-if="transactionMessage" class="notification success">
    {{ transactionMessage }}
    <template v-if="transactionHash">
      <br />
      <a
          :href="`https://sepolia.etherscan.io/tx/${transactionHash}`"
          target="_blank"
          rel="noopener"
          style="color: #3b82f6; text-decoration: underline;"
      >
        View on Etherscan
      </a>
    </template>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { CheckCircle, XCircle, Droplets, Leaf, TreePine } from 'lucide-vue-next'
import MeasureService from '@/services/MeasureService'
import { BACKEND_URL } from '@/services/session.js';

const allMeasures = ref([])

onMounted(async () => {
  try {
    allMeasures.value = await MeasureService.getAllMeasures()
  } catch (error) {
    console.error('Failed to fetch measures:', error)
  }
})

const showModal = ref(false)
const selectedPhotoUrl = ref(null)

const isLoading = ref(false)
const transactionMessage = ref('')

function openPhoto(photoUrl) {
  selectedPhotoUrl.value = `${BACKEND_URL}${photoUrl}`
  showModal.value = true
}

function closeModal() {
  showModal.value = false
  selectedPhotoUrl.value = null
}

const pendingMeasures = computed(() =>
    allMeasures.value.filter(m => m.status === 'PENDING')
)

const reviewedMeasures = computed(() =>
    allMeasures.value.filter(m => m.status === 'APPROVED' || m.status === 'REJECTED')
)

const notification = ref({ message: '', type: '' })

function showNotification(msg, type = 'success') {
  notification.value = { message: msg, type }
  setTimeout(() => {
    notification.value = { message: '', type: '' }
  }, 3000)
}

function getIcon(type) {
  switch (type) {
    case 'Rain Barrel': return Droplets
    case 'Green Roof': return Leaf
    case 'Pavement': return TreePine
    default: return Droplets
  }
}

function getIconBg(type) {
  switch (type) {
    case 'Rain Barrel': return 'icon-bg-blue'
    case 'Green Roof': return 'icon-bg-green'
    case 'Pavement': return 'icon-bg-purple'
    default: return 'icon-bg-blue'
  }
}

function getIconColor(type) {
  switch (type) {
    case 'Rain Barrel': return 'icon-blue'
    case 'Green Roof': return 'icon-green'
    case 'Pavement': return 'icon-purple'
    default: return 'icon-blue'
  }
}

function getStatusClass(status) {
  switch (status) {
    case 'PENDING': return 'pending'
    case 'APPROVED': return 'approved'
    case 'REJECTED': return 'rejected'
    default: return ''
  }
}

function getRelativeTime(timestamp) {
  const now = new Date()
  const then = new Date(timestamp)
  const diff = Math.floor((now - then) / (1000 * 60 * 60 * 24))
  return diff === 0 ? 'Today' : `${diff} day${diff > 1 ? 's' : ''} ago`
}

const transactionHash = ref('')

async function approve(id) {
  isLoading.value = true
  transactionMessage.value = ''
  transactionHash.value = ''
  try {
    const result = await MeasureService.approveMeasure(id)
    // Fetch the updated measure (with txHash)
    const updated = await MeasureService.getMeasureById(id)
    const idx = allMeasures.value.findIndex(m => m.id === id)
    if (idx !== -1) allMeasures.value[idx] = updated
    showNotification('Measure approved successfully.', 'success')
    // Extract TX hash if present
    const match = result.match(/TX:([0-9a-fA-Fx]+)/)
    if (match) {
      transactionHash.value = match[1]
      transactionMessage.value = 'Measure approved and 100 AQR tokens transferred.'
    } else {
      transactionMessage.value = result
    }
  } catch (err) {
    console.error('Failed to approve measure:', err)
    showNotification('Failed to approve measure.', 'error')
    transactionMessage.value = err.message
  } finally {
    isLoading.value = false
  }
}

async function reject(id) {
  try {
    await MeasureService.rejectMeasure(id)
    const measure = allMeasures.value.find(m => m.id === id)
    if (measure) measure.status = 'REJECTED'
    showNotification('Measure rejected.', 'error')
  } catch (err) {
    console.error('Failed to reject measure:', err)
    showNotification('Failed to reject measure.', 'error')
  }
}
</script>

<style scoped>
.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3b82f6;
  border-radius: 50%;
  width: 32px;
  height: 32px;
  animation: spin 1s linear infinite;
  margin: 1rem auto;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.photo-link {
  background: none;
  border: none;
  padding: 0;
  font: inherit;
  color: #3b82f6;
  cursor: pointer;
  text-decoration: underline;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-content {
  background: white;
  padding: 1rem;
  border-radius: 0.5rem;
  max-width: 90%;
  max-height: 90%;
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
}

.modal-content img {
  max-width: 100%;
  max-height: 70vh;
  display: block;
  margin-bottom: 1rem;
}

.close-btn {
  background-color: #ef4444;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  cursor: pointer;
}

.notification {
  margin-bottom: 1rem;
  padding: 0.75rem 1rem;
  border-radius: 0.375rem;
  font-weight: 500;
  text-align: center;
}

.notification.success {
  background-color: #d1fae5;
  color: #065f46;
  border: 1px solid #10b981;
}

.notification.error {
  background-color: #fee2e2;
  color: #991b1b;
  border: 1px solid #ef4444;
}

.status-badge {
  padding: 0.35em 0.75em;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: capitalize;
  display: inline-block;
  color: white;
}
.photo-link {
  color: #3b82f6;
  text-decoration: none;
  font-weight: 500;
}

.header {
  margin-bottom: 2rem;
}

.title {
  font-size: 1.875rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.subtitle {
  margin-top: 0.5rem;
  margin-bottom: 0.75rem;
  color: #4b5563;
  font-size: 1rem;
  font-weight: 600;
  padding: 0.7rem 1.5rem;
}

.panel {
  background-color: #ffffff;
  box-shadow: 0 1px 2px rgb(0 0 0 / 0.05);
  border-radius: 0.375rem;
  overflow-x: auto;
}

.measure-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 900px;
}

.measure-table th,
.measure-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
  font-size: 0.875rem;
}

.measure-table th {
  font-weight: 600;
  color: #374151;
  background-color: #f9fafb;
}

.icon-bg {
  height: 2.25rem;
  width: 2.25rem;
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon {
  height: 1.25rem;
  width: 1.25rem;
}

.icon-bg-blue {
  background-color: #dbeafe;
}

.icon-blue {
  color: #2563eb;
}

.icon-bg-green {
  background-color: #d1fae5;
}

.icon-green {
  color: #16a34a;
}

.icon-bg-purple {
  background-color: #ede9fe;
}

.icon-purple {
  color: #7c3aed;
}

.btn {
  font-size: 0.75rem;
  padding: 0.4rem 0.75rem;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  color: white;
  margin-right: 0.5rem;
}

.btn-icon {
  height: 1rem;
  width: 1rem;
  margin-right: 0.4rem;
  margin-bottom: 0.1rem;
  vertical-align: middle;
}

.btn.approve {
  background-color: #16a34a;
}

.btn.reject {
  background-color: #dc2626;
}

.status-badge {
  padding: 0.3em 0.6em;
  border-radius: 0.375rem;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  color: white;
  display: inline-block;
}

.status-badge.pending {
  background-color:#f59e0b;
}

.status-badge.approved {
  background-color: #10b981;
}

.status-badge.rejected {
  background-color: #ef4444;
}
</style>
