<template>
  <Navbar />
  <main class="main-container">
    <router-view />
  </main>
</template>

<script setup>
import { sessionService } from './services/session.js';
import { provide } from 'vue';
import Navbar from './components/Navbar.vue';

provide('sessionService', sessionService);
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

body {
  font-family: 'Poppins', sans-serif;
  background: radial-gradient(circle, rgba(237, 250, 244, 1) 0%, rgba(194, 222, 255, 1) 100%);
  min-height: 100vh;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.main-container {
  flex: 1;
  margin: 3rem 2rem;
}

@media (min-width: 1440px) {
  .main-container {
    margin: 3rem 15rem;
  }
}
</style>