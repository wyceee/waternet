<!-- src/components/SupervisorPanel.vue -->
<template>
  <div>
    <div class="header">
      <h1 class="title">Supervisor Approval Panel</h1>
      <p class="subtitle">Review and approve water retention measures</p>
    </div>

    <div class="panel">
      <ul class="list">
        <li
            v-for="(item, index) in approvals"
            :key="index"
            class="list-item"
        >
          <div class="item-left">
            <div :class="['icon-bg', item.iconBg]">
              <component :is="item.icon" class="icon" :class="item.iconColor" />
            </div>
            <div class="item-content">
              <div class="item-title">{{ item.title }}</div>
              <div class="item-submitter">
                Submitted by {{ item.submitter }} • {{ item.submittedAgo }}
              </div>
              <div class="item-details">{{ item.details }}</div>
            </div>
          </div>

          <div class="item-actions">
            <button class="btn approve">
              <CheckCircle class="btn-icon" />
              Approve
            </button>
            <button class="btn reject">
              <XCircle class="btn-icon" />
              Reject
            </button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { CheckCircle, XCircle, Droplets, Leaf, TreePine } from 'lucide-vue-next';

const approvals = [
  {
    icon: Droplets,
    iconBg: 'icon-bg-blue',
    iconColor: 'icon-blue',
    title: 'Rain Barrel System',
    submitter: 'John Doe',
    submittedAgo: '2 days ago',
    details: '500 gallon capacity • 123 Oak Street',
  },
  {
    icon: Leaf,
    iconBg: 'icon-bg-green',
    iconColor: 'icon-green',
    title: 'Green Roof Installation',
    submitter: 'Jane Smith',
    submittedAgo: '3 days ago',
    details: '200 sq ft • 456 Pine Avenue',
  },
  {
    icon: TreePine,
    iconBg: 'icon-bg-purple',
    iconColor: 'icon-purple',
    title: 'Permeable Pavement',
    submitter: 'Mike Johnson',
    submittedAgo: '5 days ago',
    details: '150 sq ft driveway • 789 Elm Street',
  },
];
</script>

<style scoped>
.header {
  margin-bottom: 2rem;
}

.title {
  font-size: 1.875rem; /* 3xl */
  font-weight: 700;
  color: #111827; /* gray-900 */
  margin: 0;
}

.subtitle {
  margin-top: 0.5rem;
  color: #4b5563; /* gray-600 */
  font-size: 1rem;
}

.panel {
  background-color: #ffffff;
  box-shadow: 0 1px 2px rgb(0 0 0 / 0.05);
  border-radius: 0.375rem; /* rounded-md */
  overflow: hidden;
}

.list {
  list-style: none;
  margin: 0;
  padding: 0;
  border-top: 1px solid #e5e7eb; /* gray-200 */
}

.list-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 1.5rem;
  border-bottom: 1px solid #e5e7eb; /* gray-200 */
}

.item-left {
  display: flex;
  align-items: center;
  flex: 1;
}

.icon-bg {
  height: 3rem; /* 12 */
  width: 3rem;
  border-radius: 9999px; /* full */
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.icon-bg-blue {
  background-color: #dbeafe; /* bg-blue-100 */
}

.icon-bg-green {
  background-color: #d1fae5; /* bg-green-100 */
}

.icon-bg-purple {
  background-color: #ede9fe; /* bg-purple-100 */
}

.icon {
  height: 1.5rem; /* 6 */
  width: 1.5rem;
}

.icon-blue {
  color: #2563eb; /* text-blue-600 */
}

.icon-green {
  color: #16a34a; /* text-green-600 */
}

.icon-purple {
  color: #7c3aed; /* text-purple-600 */
}

.item-content {
  margin-left: 1rem;
  flex: 1;
}

.item-title {
  font-size: 1.125rem; /* lg */
  font-weight: 500;
  color: #111827; /* gray-900 */
}

.item-submitter {
  font-size: 0.875rem; /* sm */
  color: #6b7280; /* gray-500 */
  margin-top: 0.25rem;
}

.item-details {
  font-size: 0.875rem;
  color: #4b5563; /* gray-600 */
  margin-top: 0.25rem;
}

.item-actions {
  display: flex;
  gap: 0.5rem;
}

.btn {
  display: inline-flex;
  align-items: center;
  padding: 0.5rem 0.75rem;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  font-weight: 500;
  border: none;
  cursor: pointer;
  user-select: none;
  outline-offset: 2px;
  transition: background-color 0.2s;
}

.btn-icon {
  height: 1rem; /* 4 */
  width: 1rem;
  margin-right: 0.25rem;
}

.btn.approve {
  color: #ffffff;
  background-color: #16a34a; /* green-600 */
  box-shadow: 0 0 0 2px transparent;
}

.btn.approve:hover {
  background-color: #15803d; /* green-700 */
}

.btn.approve:focus {
  box-shadow: 0 0 0 2px #4ade80; /* green-500 ring */
}

.btn.reject {
  color: #ffffff;
  background-color: #dc2626; /* red-600 */
}

.btn.reject:hover {
  background-color: #b91c1c; /* red-700 */
}

.btn.reject:focus {
  box-shadow: 0 0 0 2px #f87171; /* red-500 ring */
}
</style>
