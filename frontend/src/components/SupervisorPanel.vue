<template>
  <div>
    <div class="header">
      <h1 class="title">Supervisor Approval Panel</h1>
      <p>Review and approve water retention measures</p>
    </div>

    <div v-if="notification.message" :class="['notification', notification.type]">
      <template v-if="notification.type === 'loading'">
        <span class="mini-spinner" style="margin-right: 8px;"></span>
      </template>
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
          <th>Title</th>
          <th>Description</th>
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
          <td>{{ item.measureType }}</td>
          <td>
            <div class="description-cell">
              <span class="truncate-text">{{ truncateText(item.description, 60) }}</span>
              <div v-if="item.description.length > 60" class="tooltip-wrapper">
                <span class="info-icon">i</span>
                <div class="tooltip-text">{{ item.description }}</div>
              </div>
            </div>
          </td>
          <td>{{ item.area }} m²</td>
          <td>{{ item.capacity }} L</td>
          <td>{{ item.location }}</td>
          <td>
            <span :class="['status-badge', getStatusClass(item.status)]">{{ item.status }}</span>
          </td>
          <td>
            <button class="photo-link" @click="openPhoto(item.photoUrl)">View photo</button>
          </td>
          <td>
            <template v-if="!processingMeasures.has(item.id)">
              <button class="btn approve" @click="approve(item.id)" :disabled="processing">
                <CheckCircle class="btn-icon" /> Approve
              </button>
              <button class="btn reject" @click="reject(item.id)" :disabled="processing">
                <XCircle class="btn-icon" /> Reject
              </button>
            </template>
          </td>
        </tr>
        <tr v-if="pendingMeasures.length === 0">
          <td colspan="11" style="text-align: center; padding: 1rem; color: #6b7280;">No pending measures found.</td>
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
          <th>Title</th>
          <th>Description</th>
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
          <td>{{ item.measureType }}</td>
          <td>
            <div class="description-cell">
              <span class="truncate-text">{{ truncateText(item.description, 60) }}</span>
              <div v-if="item.description.length > 60" class="tooltip-wrapper">
                <span class="info-icon">i</span>
                <div class="tooltip-text">{{ item.description }}</div>
              </div>
            </div>
          </td>
          <td>{{ item.area }} m²</td>
          <td>{{ item.capacity }} L</td>
          <td>{{ item.location }}</td>
          <td>
            <span v-if="item.status === 'APPROVED' && !item.txHash" class="status-badge submitting">Submitting...</span>
            <span v-else :class="['status-badge', getStatusClass(item.status)]">{{ item.status }}</span>
          </td>
          <td>
            <template v-if="item.txHash">
              <a :href="`https://sepolia.etherscan.io/tx/${item.txHash}`" target="_blank" rel="noopener" style="color: #3b82f6; text-decoration: underline;">View TX</a>
            </template>
            <template v-else>-</template>
          </td>
          <td>
            <button class="photo-link" @click="openPhoto(item.photoUrl)">View photo</button>
          </td>
        </tr>
        <tr v-if="reviewedMeasures.length === 0">
          <td colspan="11" style="text-align: center; padding: 1rem; color: #6b7280;">No reviewed measures available.</td>
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

  <!-- Transaction Popup -->
  <div v-if="transactionPopup.visible" class="transaction-popup">
    <button class="close-popup" @click="closeTransactionPopup">×</button>

    <template v-if="transactionPopup.status === 'submitted'">
      <div class="spinner inline"></div>
      <strong style="margin-left: 0.5rem;">Transaction submitted...</strong><br />
    </template>
    <template v-else>
      <strong>✓ Transaction completed</strong><br />
    </template>

    {{ transactionPopup.message }}<br />
    <a
        v-if="transactionPopup.hash"
        :href="`https://sepolia.etherscan.io/tx/${transactionPopup.hash}`"
        target="_blank"
        rel="noopener"
    >View on Etherscan</a>
    <div class="progress-bar-wrapper" v-if="transactionPopup.status === 'completed'">
      <div class="progress-bar" :style="{ width: transactionPopup.progress + '%' }"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { CheckCircle, XCircle, Droplets, Leaf, TreePine } from 'lucide-vue-next'
import MeasureService from '@/services/MeasureService'
import { BACKEND_URL } from '@/services/session.js'

const allMeasures = ref([])
const loadingStates = ref({})
const processingMeasures = ref(new Set())
const showModal = ref(false)
const selectedPhotoUrl = ref(null)

const notification = ref({ message: '', type: '' })
const processing = ref(false)

const transactionPopup = ref({
  visible: false,
  status: 'submitted',
  message: '',
  hash: '',
  progress: 100,
  intervalId: null
})

onMounted(async () => {
  allMeasures.value = await MeasureService.getAllMeasures()
})

const pendingMeasures = computed(() => allMeasures.value.filter(m => m.status === 'PENDING'))
const reviewedMeasures = computed(() => allMeasures.value.filter(m => ['APPROVED', 'REJECTED'].includes(m.status)))

function truncateText(text, maxLength) {
  return text.length > maxLength ? text.slice(0, maxLength) + '…' : text
}

function getIcon(type) {
  return {
    'Rain Barrel': Droplets,
    'Green Roof': Leaf,
    'Pavement': TreePine
  }[type] || Droplets
}

function getIconBg(type) {
  return {
    'Rain Barrel': 'icon-bg-blue',
    'Green Roof': 'icon-bg-green',
    'Pavement': 'icon-bg-purple'
  }[type] || 'icon-bg-blue'
}

function getIconColor(type) {
  return {
    'Rain Barrel': 'icon-blue',
    'Green Roof': 'icon-green',
    'Pavement': 'icon-purple'
  }[type] || 'icon-blue'
}

function getStatusClass(status) {
  return {
    'PENDING': 'pending',
    'APPROVED': 'approved',
    'REJECTED': 'rejected'
  }[status] || ''
}

function getRelativeTime(timestamp) {
  const now = new Date()
  const then = new Date(timestamp)
  const diff = Math.floor((now - then) / (1000 * 60 * 60 * 24))
  return diff === 0 ? 'Today' : `${diff} day${diff > 1 ? 's' : ''} ago`
}

function openPhoto(url) {
  selectedPhotoUrl.value = `${BACKEND_URL}${url}`
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

function showNotification(msg, type = 'success') {
  notification.value = { message: msg, type }
  setTimeout(() => (notification.value = { message: '', type: '' }), 3000)
}

function closeTransactionPopup() {
  clearInterval(transactionPopup.value.intervalId)
  transactionPopup.value.visible = false
}

function showTransactionPopup(message, hash = '', status = 'submitted') {
  transactionPopup.value.visible = true
  transactionPopup.value.status = status
  transactionPopup.value.message = message
  transactionPopup.value.hash = hash
  transactionPopup.value.progress = 100

  if (status === 'completed') {
    const interval = setInterval(() => {
      transactionPopup.value.progress -= 2
      if (transactionPopup.value.progress <= 0) {
        closeTransactionPopup()
      }
    }, 120)
    transactionPopup.value.intervalId = interval
  }
}

async function approve(id) {
  if (!confirm('Are you sure you want to approve this measure?')) return
  processing.value = true
  showTransactionPopup('Waiting for confirmation...', '', 'submitted')

  try {
    const result = await MeasureService.approveMeasure(id)

    const match = result.match(/TX:([0-9a-fA-Fx]+)/)
    const hash = match ? match[1] : ''
    if (hash) transactionPopup.value.hash = hash

    const interval = setInterval(async () => {
      const updated = await MeasureService.getMeasureById(id)
      if (updated.txHash) {
        clearInterval(interval)
        const idx = allMeasures.value.findIndex(m => m.id === id)
        if (idx !== -1) allMeasures.value[idx] = updated
        showTransactionPopup('Transaction completed.', updated.txHash, 'completed')
        processing.value = false
      }
    }, 3000)
  } catch (err) {
    console.error(err)
    showNotification('Approval failed.', 'error')
    processing.value = false
    closeTransactionPopup()
  }
}

async function reject(id) {
  if (!confirm('Are you sure you want to reject this measure?')) return
  processing.value = true
  loadingStates.value[id] = true
  try {
    await MeasureService.rejectMeasure(id)
    const measure = allMeasures.value.find(m => m.id === id)
    if (measure) measure.status = 'REJECTED'
    showNotification('Measure rejected.', 'error')
  } catch (err) {
    showNotification('Failed to reject measure.', 'error')
  } finally {
    loadingStates.value[id] = false
  }
}
</script>

<style scoped>
.description-cell {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  max-width: 200px;
}

.truncate-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.tooltip-wrapper {
  position: relative;
  display: inline-block;
}


.info-icon {
  font-family: 'Inter', 'Segoe UI', sans-serif;
  background-color: rgba(37, 99, 235, 0.61);
  color: white;
  border-radius: 9999px;
  padding: 0.1rem 0.6rem;
  font-size: 1rem;
  cursor: pointer;
}

.tooltip-text {
  visibility: hidden;
  opacity: 0;
  width: 200px;
  background-color: #111827;
  color: #fff;
  text-align: left;
  padding: 0.5rem;
  border-radius: 0.375rem;
  position: absolute;
  z-index: 10;
  bottom: 125%;
  left: 50%;
  transform: translateX(-50%);
  transition: opacity 0.3s ease;
  font-size: 0.75rem;
  line-height: 1.2;
  white-space: normal;
}

.tooltip-wrapper:hover .tooltip-text {
  visibility: visible;
  opacity: 1;
}

.close-popup {
  position: absolute;
  top: 10px;
  right: 10px;
  background: #3b82f6;
  border: none;
  border-radius: 50px;
  color: white;
  font-size: 1.25rem;
  cursor: pointer;
}

.spinner.inline {
  border: 3px solid #f3f3f3;
  border-top: 3px solid #3b82f6;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  animation: spin 1s linear infinite;
  display: inline-block;
  vertical-align: middle;
  margin-right: 0.5rem;
}

.mini-spinner {
  border: 2px solid white;
  border-top: 2px solid #3b82f6;
  border-radius: 50%;
  width: 14px;
  height: 14px;
  animation: spin 1s linear infinite;
  display: inline-block;
  vertical-align: middle;
}

.status-badge.submitting {
  background-color: #3b82f6;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
.transaction-popup {
  position: fixed;
  top: 1rem;
  right: 1rem;
  background-color: #1f2937;
  color: white;
  padding: 1rem 2.55rem 1rem 1.25rem;
  border-radius: 0.5rem;
  box-shadow: 0 4px 12px rgba(0,0,0,0.3);
  z-index: 9999;
  max-width: 360px;
  font-size: 0.875rem;
  overflow: hidden;
  transition: opacity 0.3s ease;
}

.progress-bar-wrapper {
  width: 100%;
  background-color: rgba(255, 255, 255, 0.15);
  height: 4px;
  border-radius: 4px;
  margin-top: 0.5rem;
  overflow: hidden;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.progress-bar {
  height: 100%;
  background-color: #3b82f6;
  transition: width 0.1s linear;
}

.transaction-popup a {
  color: #3b82f6;
  text-decoration: underline;
  font-size: 0.875rem;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
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
