<template>
  <div>
    <!-- Loading spinner -->
    <div v-if="!isLoaded" style="text-align: center; padding: 4rem;">
      <span class="mini-spinner"></span>
    </div>

    <!-- Main content -->
    <div v-else>
      <div class="header">
        <h1 class="title">Wallet</h1>
        <p class="subtitle">Manage your Hydrewards tokens</p>
      </div>

      <div class="balance-card">
        <div class="balance-content">
          <div>
            <p class="balance-label">Total Balance</p>
            <p class="balance-amount">
              <template v-if="userBalance !== null">
                {{ (BigInt(userBalance) / 10n ** 18n).toLocaleString() }} HYD
              </template>
              <template v-else>
                Loading...
              </template>
            </p>
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
          <li
              v-for="(txn, index) in transactions"
              :key="index"
              class="transaction-item"
          >
            <div class="transaction-row">
              <div class="transaction-left">
                <div class="icon-wrapper">
                  <div class="icon-bg">
                    <Plus class="transaction-icon" />
                  </div>
                </div>
                <div class="transaction-info">
                  <div class="transaction-title">{{ txn.title }}</div>
                  <div class="transaction-date-tx">
                    {{ txn.date }} •
                    <a
                        :href="`https://sepolia.etherscan.io/tx/${txn.tx}`"
                        target="_blank"
                        rel="noopener"
                        style="color: #3b82f6; text-decoration: underline;"
                    >View TX</a>
                  </div>
                </div>
              </div>
              <div class="transaction-right">
                <div class="transaction-amount">+{{ txn.amount }} HYD</div>
                <div class="transaction-usd">{{ txn.usd }}</div>
              </div>
            </div>
          </li>

          <li v-if="transactions.length === 0" class="transaction-item" style="text-align: center; color: #6b7280;">
            No reward transactions yet.
          </li>

          <li v-if="hasMore" style="text-align:center; padding: 1rem;">
            <button @click="loadNextPage" :disabled="loading" style="padding: 0.5rem 1rem;">
              {{ loading ? 'Loading...' : 'Load More' }}
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import UserBalanceService from '../services/UserBalanceService.js';
import MeasureService from "@/services/MeasureService.js";
import { SessionService } from '@/services/SessionService';
import { Coins, Plus } from 'lucide-vue-next';

const sessionService = new SessionService('/api', 'session_token');
const userId = sessionService.user?.id;
const userBalance = ref(null);

const transactions = ref([]);
const page = ref(0);
const size = 10;
const isLoaded = ref(false);
const loading = ref(false);
const hasMore = ref(true);
const totalItems = ref(0);

function formatDate(dateStr) {
  const now = new Date();
  const date = new Date(dateStr);
  const diff = Math.floor((now - date) / 1000);

  if (diff < 60) return `${diff} sec ago`;
  if (diff < 3600) return `${Math.floor(diff / 60)} min ago`;
  if (diff < 86400) return `${Math.floor(diff / 3600)} hrs ago`;

  return date.toLocaleDateString();
}

async function loadNextPage() {
  if (loading.value || !hasMore.value) return;

  loading.value = true;
  try {
    const response = await MeasureService.getUserRewardsPaginated(userId, page.value, size);

    const newTxns = response.data;
    totalItems.value = response.total;

    if (newTxns.length < size) hasMore.value = false;
    else page.value++;

    const formatted = newTxns.map(txn => ({
      ...txn,
      date: formatDate(txn.date),
      usd: ''
    }));

    transactions.value.push(...formatted);
  } catch (error) {
    console.error('Failed to load transactions:', error);
  } finally {
    loading.value = false;
  }
}

onMounted(async () => {
  if (userId) {
    try {
      userBalance.value = await UserBalanceService.getUserBalance(userId);
      await loadNextPage();
    } catch (error) {
      console.error('Wallet load failed:', error);
    } finally {
      isLoaded.value = true;
    }
  }
});
</script>

<style scoped>
.mini-spinner {
  display: inline-block;
  width: 24px;
  height: 24px;
  border: 3px solid rgba(0,0,0,0.1);
  border-top-color: #3b82f6;
  border-radius: 50%;
  animation: spin 1s ease infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
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
  color: #4b5563;
  font-size: 1rem;
}

.balance-card {
  background: linear-gradient(90deg, #3b82f6, #2563eb);
  border-radius: 0.5rem;
  box-shadow: 0 10px 15px -3px rgb(59 130 246 / 0.5), 0 4px 6px -4px rgb(37 99 235 / 0.5);
  padding: 1.5rem;
  color: white;
  margin-bottom: 2rem;
}

.balance-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.balance-label {
  color: #bfdbfe;
  font-size: 0.875rem;
  margin: 0;
}

.balance-amount {
  font-size: 1.875rem;
  font-weight: 700;
  margin: 0.25rem 0 0 0;
}

.balance-usd {
  color: #bfdbfe;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.balance-icon .icon {
  height: 3rem;
  width: 3rem;
  color: #bfdbfe;
}

.transactions-panel {
  background: white;
  box-shadow: 0 1px 2px rgb(0 0 0 / 0.05);
  border-radius: 0.375rem;
  overflow: hidden;
}

.transactions-header {
  padding: 1.25rem 1rem 1rem;
}

.transactions-title {
  font-size: 1.125rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
  line-height: 1.4;
}

.transactions-subtitle {
  margin-top: 0.25rem;
  max-width: 32rem;
  font-size: 0.875rem;
  color: #6b7280;
  margin-bottom: 0;
}

.transactions-list {
  list-style: none;
  margin: 0;
  padding: 0;
  border-top: 1px solid #e5e7eb;
}

.transaction-item {
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #e5e7eb;
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
  height: 2.5rem;
  width: 2.5rem;
}

.icon-bg {
  height: 2.5rem;
  width: 2.5rem;
  border-radius: 9999px;
  background-color: #d1fae5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.transaction-icon {
  height: 1.25rem;
  width: 1.25rem;
  color: #16a34a;
}

.transaction-info {
  margin-left: 1rem;
}

.transaction-title {
  font-size: 0.875rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
}

.transaction-date-tx {
  font-size: 0.875rem;
  margin-top: 0.125rem;
}

.transaction-right {
  text-align: right;
}

.transaction-amount {
  font-size: 0.875rem;
  font-weight: 500;
  color: #16a34a;
}

.transaction-usd {
  font-size: 0.875rem;
  color: #6b7280;
}
</style>
