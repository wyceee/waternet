<template>
  <nav class="navbar">
    <div class="container">
      <div class="nav-content">
        <div class="brand">
          <img src="@/assets/navbarlogo.png" alt="Logo" class="logo" />
        </div>
        <div class="nav-links">
          <router-link to="/" class="nav-link" exact-active-class="active">Dashboard</router-link>
          <router-link to="/register" class="nav-link" exact-active-class="active">Register Measure</router-link>
          <router-link to="/supervisor" class="nav-link" exact-active-class="active">Supervisor Panel</router-link>
          <router-link to="/wallet" class="nav-link" exact-active-class="active">Wallet</router-link>
        </div>
        <div class="auth-buttons">
          <router-link
              v-if="!isLoggedIn"
              to="/login"
              class="auth-button login"
          >
            Login
          </router-link>
          <router-link
              v-if="!isLoggedIn"
              to="/signup"
              class="auth-button signup"
          >
            Signup
          </router-link>
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
import {createRouter as $router} from "vue-router";

const isLoggedIn = ref(false);

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
  background-color: #2563eb;
  color: white;
}
.auth-button.login:hover {
  background-color: #1d4ed8;
}
.auth-button.signup {
  background-color: #10b981;
  color: white;
}
.auth-button.signup:hover {
  background-color: #059669;
}
.auth-button.signout {
  background-color: #dc2626;
  color: white;
}
.auth-button.signout:hover {
  background-color: #b91c1c;
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
  color: #6b7280;
  font-size: 0.875rem;
  font-weight: 500;
  padding: 4px 0;
  text-decoration: none;
}

.nav-link:hover {
  color: #374151;
  border-bottom: 2px solid #d1d5db;
}

.nav-link.active {
  border-bottom: 2px solid #3b82f6;
  color: #111827;
}
</style>