<script setup>
import { ref } from 'vue';
import Navbar from './components/Navbar.vue';
import Dashboard from './components/Dashboard.vue';
import SupervisorPanel from './components/SupervisorPanel.vue';
import Wallet from './components/Wallet.vue';
import RegisterMeasure from './components/RegisterMeasure.vue';

const currentPage = ref('dashboard');
const isWalletConnected = ref(false);

const toggleWallet = () => {
  isWalletConnected.value = !isWalletConnected.value;
};
</script>

<template>
  <Navbar
      :currentPage="currentPage"
      :isWalletConnected="isWalletConnected"
      @changePage="currentPage = $event"
      @toggleWallet="toggleWallet"
  />

  <main class="main-container">
    <Dashboard v-if="currentPage === 'dashboard'" />
    <SupervisorPanel v-if="currentPage === 'supervisor'" />
    <Wallet v-if="currentPage === 'wallet'" />
    <RegisterMeasure v-if="currentPage === 'registerMeasure'" />
  </main>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

body {
  font-family: 'Poppins', sans-serif;
  background: #edfaf4;
  background: radial-gradient(circle, rgba(237, 250, 244, 1) 0%, rgba(194, 222, 255, 1) 100%);
  min-height: 100vh;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.main-container {
  flex: 1;
  margin: 3rem 2rem; /* Default margin for smaller screens */
}

@media (min-width: 1440px) {
  .main-container {
    margin: 3rem 15rem; /* Extra-large screens */
  }
}
</style>