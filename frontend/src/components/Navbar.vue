<template>
  <nav class="navbar">
    <div class="container">
      <div class="nav-content">
        <div class="brand">
          <img src="@/assets/navbarlogo.png" alt="Logo" class="logo" />
        </div>
        <div class="nav-links">
          <button
              @click="$emit('changePage', 'dashboard')"
              :class="currentPage === 'dashboard' ? 'nav-link active' : 'nav-link'"
          >
            Dashboard
          </button>
          <button
              @click="$emit('changePage', 'registerMeasure')"
              :class="currentPage === 'registerMeasure' ? 'nav-link active' : 'nav-link'"
          >
            Register Measure
          </button>
          <button
              @click="$emit('changePage', 'supervisor')"
              :class="currentPage === 'supervisor' ? 'nav-link active' : 'nav-link'"
          >
            Supervisor Panel
          </button>
          <button
              @click="$emit('changePage', 'wallet')"
              :class="currentPage === 'wallet' ? 'nav-link active' : 'nav-link'"
          >
            Wallet
          </button>
        </div>
        <div class="auth-buttons">
          <button
              v-if="!isLoggedIn"
              @click="navigateTo('login')"
              class="auth-button login"
          >
            Login
          </button>
          <button
              v-if="!isLoggedIn"
              @click="navigateTo('signup')"
              class="auth-button signup"
          >
            Signup
          </button>
          <button
              v-if="isLoggedIn"
              @click="signOut"
              class="auth-button signout"
          >
            Signout
          </button>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue';

defineProps({
  currentPage: String,
});

const isLoggedIn = ref(false);

function navigateTo(page) {
  $emit('changePage', page);
}

function signOut() {
  isLoggedIn.value = false;
}

</script>

<style>
.auth-buttons {
  display: flex;
  gap: 8px;
}

.auth-button {
  display: inline-flex;
  align-items: center;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
}

.auth-button.login {
  background-color: #2563eb; /* blue-600 */
  color: white;
}

.auth-button.login:hover {
  background-color: #1d4ed8; /* blue-700 */
}

.auth-button.signup {
  background-color: #10b981; /* green-500 */
  color: white;
}

.auth-button.signup:hover {
  background-color: #059669; /* green-600 */
}

.auth-button.signout {
  background-color: #dc2626; /* red-600 */
  color: white;
}

.auth-button.signout:hover {
  background-color: #b91c1c; /* red-700 */
}

.navbar {
  background-color: white;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  border-bottom: 1px solid #e5e7eb;
}

.container {
  max-width: 1120px;
  margin: 0 auto;
  padding: 0 16px;
}

.nav-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
}

.brand {
  display: flex;
  align-items: center;
}

.logo {
  height: 200px;
  width: 200px;
  color: #2563eb; /* blue-600 */
}

.brand-text {
  margin-left: 8px;
  font-size: 1.25rem;
  font-weight: bold;
  color: #111827; /* gray-900 */
}

.nav-links {
  display: none;
}

@media (min-width: 640px) {
  .nav-links {
    display: flex;
    gap: 32px;
    margin-left: 24px;
  }
}

.nav-link {
  border-bottom: 2px solid transparent;
  color: #6b7280; /* gray-500 */
  font-size: 0.875rem;
  font-weight: 500;
  padding: 4px 0;
  cursor: pointer;
  background: none;
  border: none;
}

.nav-link:hover {
  color: #374151; /* gray-700 */
  border-bottom: 2px solid #d1d5db; /* gray-300 */
}

.nav-link.active {
  border-bottom: 2px solid #3b82f6; /* blue-500 */
  color: #111827; /* gray-900 */
}
</style>
