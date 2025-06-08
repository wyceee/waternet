<!-- src/components/Wallet.vue -->
<template>
  <div>
    <div class="header">
      <h1 class="title">Wallet</h1>
      <p class="subtitle">Manage your AquaRewards tokens</p>
    </div>

    <div class="balance-card">
      <div class="balance-content">
        <div>
          <p class="balance-label">Total Balance</p>
          <p class="balance-amount">1,250 AQR</p>
          <p class="balance-usd">≈ $125.00 USD</p>
        </div>
        <div class="balance-icon">
          <Coins class="icon" />
        </div>
      </div>
    </div>

    <div class="transactions-panel">
      <div class="transactions-header">
        <h3 class="transactions-title">Recent Transactions</h3>
        <p class="transactions-subtitle">Your latest token transactions</p>
      </div>
      <ul class="transactions-list">
        <li v-for="(txn, index) in transactions" :key="index" class="transaction-item">
          <div class="transaction-row">
            <div class="transaction-left">
              <div class="icon-wrapper">
                <div class="icon-bg">
                  <Plus class="transaction-icon" />
                </div>
              </div>
              <div class="transaction-info">
                <div class="transaction-title">{{ txn.title }}</div>
                <div class="transaction-date-tx">{{ txn.date }} • Tx: {{ txn.tx }}</div>
              </div>
            </div>
            <div class="transaction-right">
              <div class="transaction-amount">{{ txn.amount }}</div>
              <div class="transaction-usd">{{ txn.usd }}</div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { Coins, Plus } from 'lucide-vue-next';

const transactions = [
  { title: 'Reward for Green Roof', date: '2 days ago', tx: '0x1a2b3c...', amount: '+75 AQR', usd: '$7.50' },
  { title: 'Reward for Rain Garden', date: '1 week ago', tx: '0x4d5e6f...', amount: '+50 AQR', usd: '$5.00' },
  { title: 'Reward for Permeable Pavement', date: '2 weeks ago', tx: '0x7g8h9i...', amount: '+100 AQR', usd: '$10.00' }
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

.balance-card {
  background: linear-gradient(90deg, #3b82f6, #2563eb); /* from-blue-500 to-blue-600 */
  border-radius: 0.5rem; /* rounded-lg */
  box-shadow: 0 10px 15px -3px rgb(59 130 246 / 0.5), 0 4px 6px -4px rgb(37 99 235 / 0.5); /* shadow-lg */
  padding: 1.5rem; /* p-6 */
  color: white;
  margin-bottom: 2rem;
}

.balance-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.balance-label {
  color: #bfdbfe; /* text-blue-100 */
  font-size: 0.875rem; /* text-sm */
  margin: 0;
}

.balance-amount {
  font-size: 1.875rem; /* text-3xl */
  font-weight: 700;
  margin: 0.25rem 0 0 0;
}

.balance-usd {
  color: #bfdbfe; /* text-blue-100 */
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.balance-icon .icon {
  height: 3rem; /* h-12 */
  width: 3rem;  /* w-12 */
  color: #bfdbfe; /* text-blue-200 */
}

.transactions-panel {
  background: white;
  box-shadow: 0 1px 2px rgb(0 0 0 / 0.05);
  border-radius: 0.375rem; /* sm:rounded-md */
  overflow: hidden;
}

.transactions-header {
  padding: 1.25rem 1rem 1rem; /* px-4 py-5 sm:px-6 */
}

.transactions-title {
  font-size: 1.125rem; /* text-lg */
  font-weight: 500;
  color: #111827; /* gray-900 */
  margin: 0;
  line-height: 1.4;
}

.transactions-subtitle {
  margin-top: 0.25rem;
  max-width: 32rem; /* max-w-2xl */
  font-size: 0.875rem; /* text-sm */
  color: #6b7280; /* gray-500 */
  margin-bottom: 0;
}

.transactions-list {
  list-style: none;
  margin: 0;
  padding: 0;
  border-top: 1px solid #e5e7eb; /* divide-gray-200 */
}

.transaction-item {
  padding: 1rem 1.5rem; /* px-4 py-4 sm:px-6 */
  border-bottom: 1px solid #e5e7eb; /* divide-gray-200 */
}

.transaction-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.transaction-left {
  display: flex;
  align-items: center;
}

.icon-wrapper {
  flex-shrink: 0;
  height: 2.5rem; /* h-10 */
  width: 2.5rem;  /* w-10 */
}

.icon-bg {
  height: 2.5rem;
  width: 2.5rem;
  border-radius: 9999px; /* full */
  background-color: #d1fae5; /* bg-green-100 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.transaction-icon {
  height: 1.25rem; /* h-5 */
  width: 1.25rem;  /* w-5 */
  color: #16a34a; /* text-green-600 */
}

.transaction-info {
  margin-left: 1rem;
}

.transaction-title {
  font-size: 0.875rem; /* text-sm */
  font-weight: 500;
  color: #111827; /* gray-900 */
  margin: 0;
}

.transaction-date-tx {
  font-size: 0.875rem; /* text-sm */
  color: #6b7280; /* gray-500 */
  margin-top: 0.125rem;
}

.transaction-right {
  text-align: right;
}

.transaction-amount {
  font-size: 0.875rem; /* text-sm */
  font-weight: 500;
  color: #16a34a; /* text-green-600 */
}

.transaction-usd {
  font-size: 0.875rem;
  color: #6b7280; /* text-gray-500 */
}
</style>
