<template>
  <div>
    <div class="header-section">
      <h1 class="dashboard-title">Dashboard</h1>
      <p class="dashboard-subtitle">Track your water retention measures and rewards</p>
    </div>

    <div class="stats-grid">
      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper gray-icon">
              <BarChart3 class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Total Measures</dt>
                <dd class="value">{{ total }}</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper green-icon">
              <CheckCircle class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Approved</dt>
                <dd class="value">{{ approved }}</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper yellow-icon">
              <Clock class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Pending</dt>
                <dd class="value">{{ pending }}</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper blue-icon">
              <Coins class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Tokens Earned</dt>
                <dd class="value">
                  <template v-if="userBalance !== null">
                    {{ (BigInt(userBalance) / 10n ** 18n).toLocaleString() }} HYD
                  </template>
                  <template v-else>
                    Loading...
                  </template>
                </dd>
              </dl>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="recent-measures">
      <div class="recent-header">
        <h3 class="recent-title">Your Recent Measures</h3>
        <p class="recent-subtitle">Latest water retention measures submitted</p>
      </div>
      <ul class="measures-list">
        <li
            v-for="measure in userMeasures"
            :key="measure.id"
            class="measure-item"
            @click="toggleExpand(measure.id)"
        >
          <div class="measure-info">
            <div :class="['avatar', getIconBg(measure.measureType)]">
              <component
                  :is="getIcon(measure.measureType)"
                  class="avatar-icon"
                  :class="getIconColor(measure.measureType)"
              />
            </div>
            <div class="measure-text">
              <div class="measure-header">
                <div class="measure-name">{{ measure.description }}</div>
                <svg
                    class="expand-icon"
                    :class="{ rotated: expandedMeasureId === measure.id }"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                >
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
              </div>
              <div class="measure-date">Submitted {{ getRelativeTime(measure.timestamp) }}</div>
            </div>
          </div>
          <div v-show="expandedMeasureId === measure.id" class="measure-extra-info">
            <div class="measure-location">Location: {{ measure.location }}</div>
            <div class="measure-area">Area: {{ measure.area }} m²</div>
            <div class="measure-capacity">Capacity: {{ measure.capacity }} liters</div>
            <div v-if="measure.photoUrl" class="measure-photo">
              <img
                  v-if="measure.photoUrl"
                  :src="`${BACKEND_URL}${measure.photoUrl}`"
                  alt="Measure Photo"
                  class="photo-preview"
                  @click.stop="openModalPhoto(measure.photoUrl)"
                  style="cursor: pointer"
              />
            </div>
          </div>
          <span class="status" :class="measure.status.toLowerCase()">
        {{ measure.status }}
      </span>
        </li>
        <li v-if="userMeasures.length === 0" class="measure-item">
          <div class="measure-text">No measures found.</div>
        </li>
      </ul>
    </div>
  </div>
  <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <img :src="selectedPhotoUrl" alt="Enlarged Measure Photo" />
      <button class="close-btn" @click="closeModal">Close</button>
    </div>
  </div>
</template>

<script setup>
import { BarChart3, CheckCircle, Clock, Coins, Droplets, Leaf, TreePine } from 'lucide-vue-next';
import {SessionService} from '@/services/SessionService';
import MeasureService from '@/services/MeasureService';
import UserBalanceService from "@/services/UserBalanceService.js";
import {computed, onMounted, ref} from 'vue';
import { BACKEND_URL } from '@/services/session.js';

const expandedMeasureId = ref(null);
const sessionService = new SessionService('/api', 'session_token');
const userId = sessionService.user?.id;

const userMeasures = ref([]);
const userBalance = ref(null);

onMounted(async () => {
  if (!userId) return;

  try {
    userMeasures.value = await MeasureService.getMeasuresByUserId(userId);
    userBalance.value = await UserBalanceService.getUserBalance(userId);
    console.log('User measures loaded:', userMeasures.value);
  } catch (err) {
    console.error('Failed to load user measures:', err);
  }
});

const showModal = ref(false);
const selectedPhotoUrl = ref(null);

function openModalPhoto(photoUrl) {
  selectedPhotoUrl.value = `${BACKEND_URL}${photoUrl}`;
  showModal.value = true;
}

function closeModal() {
  showModal.value = false;
  selectedPhotoUrl.value = null;
}

const total = computed(() => userMeasures.value.length);
const approved = computed(() => userMeasures.value.filter(m => m.status === 'APPROVED').length);
const pending = computed(() => userMeasures.value.filter(m => m.status === 'PENDING').length);

function getIcon(type) {
  switch (type) {
    case 'Rain Barrel': return Droplets;
    case 'Green Roof': return Leaf;
    case 'Pavement': return TreePine;
    default: return Droplets;
  }
}

function toggleExpand(measureId) {
  expandedMeasureId.value = expandedMeasureId.value === measureId ? null : measureId;
}

function getIconBg(type) {
  switch (type) {
    case 'Rain Barrel': return 'blue-bg';
    case 'Green Roof': return 'green-bg';
    case 'Pavement': return 'purple-bg';
    default: return 'blue-bg';
  }
}

function getIconColor(type) {
  switch (type) {
    case 'Rain Barrel': return 'blue-icon-dark';
    case 'Green Roof': return 'green-icon-dark';
    case 'Pavement': return 'purple-icon-dark';
    default: return 'blue-icon-dark';
  }
}

function getRelativeTime(timestamp) {
  const now = new Date();
  const then = new Date(timestamp);
  const diff = Math.floor((now - then) / (1000 * 60 * 60 * 24));
  return diff === 0 ? 'Today' : `${diff} day${diff > 1 ? 's' : ''} ago`;
}
</script>
<style>
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

.measure-item {
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.measure-item:hover {
  background-color: #f3f4f6;
}

.measure-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.expand-icon {
  width: 16px;
  height: 16px;
  margin-left: 8px;
  color: #6b7280;
  transition: transform 0.3s ease;
}

.expand-icon.rotated {
  transform: rotate(180deg);
}

.header-section {
  margin-bottom: 32px;
}

.dashboard-title {
  font-size: 1.875rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.dashboard-subtitle {
  margin-top: 8px;
  color: #4b5563;
  font-size: 1rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 20px;
  margin-bottom: 32px;
}

@media (min-width: 640px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .stats-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

.card {
  background-color: #ffffff;
  overflow: hidden;
  box-shadow: 0 1px 3px rgb(0 0 0 / 0.1),
  0 1px 2px rgb(0 0 0 / 0.06);
  border-radius: 0.5rem;
}

.card-content {
  padding: 20px;
}

.card-row {
  display: flex;
  align-items: center;
}

.measure-extra-info {
  width: 50rem;
  margin-top: 8px;
  padding: 12px;
  background-color: #f9fafb;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}

.measure-location,
.measure-area,
.measure-capacity {
  font-size: 0.875rem;
  color: #374151;
  margin-bottom: 8px;
}

.measure-photo {
  margin-top: 12px;
}

.photo-preview {
  max-width: 100px;
  max-height: 100px;
  border-radius: 8px;
  object-fit: cover;
}

.icon-wrapper {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.gray-icon {
  color: #9ca3af;
}

.green-icon {
  color: #34d399;
}

.yellow-icon {
  color: #fbbf24;
}

.blue-icon {
  color: #60a5fa;
}

.icon {
  width: 24px;
  height: 24px;
}

.text-wrapper {
  margin-left: 20px;
  flex: 1 1 0%;
  min-width: 0;
}

.label {
  font-size: 0.875rem;
  font-weight: 500;
  color: #6b7280;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0;
}

.value {
  font-size: 1.125rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
}

.recent-measures {
  background-color: #fff;
  box-shadow: 0 1px 3px rgb(0 0 0 / 0.1),
  0 1px 2px rgb(0 0 0 / 0.06);
  overflow: hidden;
  border-radius: 0.375rem;
}

.recent-header {
  padding: 20px 16px;
}

.recent-title {
  font-size: 1.125rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
  line-height: 1.5rem;
}

.recent-subtitle {
  margin-top: 4px;
  max-width: 32rem;
  font-size: 0.875rem;
  color: #6b7280;
  margin-bottom: 0;
}

.measures-list {
  list-style: none;
  margin: 0;
  padding: 0;
  border-top: 1px solid #e5e7eb;
}

.measure-item {
  padding: 16px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e5e7eb;
}

.measure-info {
  display: flex;
  align-items: center;
}

.avatar {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.green-bg {
  background-color: #d1fae5;
}

.blue-bg {
  background-color: #bfdbfe;
}

.purple-bg {
  background-color: #e9d5ff;
}

.avatar-icon {
  width: 20px;
  height: 20px;
}

.green-icon-dark {
  color: #059669;
}

.blue-icon-dark {
  color: #2563eb;
}

.purple-icon-dark {
  color: #7c3aed;
}

.measure-text {
  margin-left: 16px;
}

.measure-name {
  font-size: 0.875rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
}

.measure-date {
  font-size: 0.875rem;
  color: #6b7280;
  margin: 0;
}

.status {
  padding: 2px 8px;
  font-size: 0.75rem;
  line-height: 1.25rem;
  font-weight: 600;
  border-radius: 9999px;
  display: inline-flex;
  align-items: center;
  white-space: nowrap;
}

.approved {
  background-color: #d1fae5;
  color: #065f46;
}

.pending {
  background-color: #fef3c7;
  color: #92400e;
}

.rejected {
  background-color: #fee2e2;
  color: #991b1b;
}

</style>
